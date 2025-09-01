package com.example.lifeup.application.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.lifeup.adapters.outbound.persistence.JpaUserEntity;
import com.example.lifeup.adapters.outbound.persistence.SpringDataUserRepository;
import com.example.lifeup.domain.model.DailyTask;
import com.example.lifeup.domain.model.RecurrenceType;
import com.example.lifeup.domain.model.TaskDifficulty;
import com.example.lifeup.domain.port.out.DailyTaskRepositoryPort;
import com.example.lifeup.domain.port.out.UserRepositoryPort;

@Service
public class DailyTaskService {
    private final DailyTaskRepositoryPort repository;
    private final UserRepositoryPort userRepository;

    @Autowired
    private UserService userService;

    @Autowired 
    private SpringDataUserRepository springUserRepo;

    // Se ejecuta todos los días a medianoche
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledRefreshDailyTasks() {
        JpaUserEntity jpaUser = currentUser();
        refreshDailyTasks(jpaUser);
    }

    private JpaUserEntity currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return springUserRepo.findByEmail(email).orElseThrow();
    }

    public DailyTaskService(DailyTaskRepositoryPort repository, UserRepositoryPort userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public DailyTask createDailyTask(String title, String description, TaskDifficulty difficulty, 
                                     LocalDate startDate, RecurrenceType recurrenceType, int repeatEvery, List<DayOfWeek> daysOfWeek){
        int points = calculatePoints(difficulty);
        JpaUserEntity jpaUser = currentUser();

        DailyTask dailyTask = new DailyTask(UUID.randomUUID(), jpaUser, title, description, false, null, points, difficulty, startDate, recurrenceType, repeatEvery, daysOfWeek);
        return repository.save(dailyTask);
    }

    public List<DailyTask> findAll(){
        JpaUserEntity jpaUser = currentUser();
        refreshDailyTasks(jpaUser);
        return repository.findByUser(jpaUser);
    }

    public DailyTask updateDailyTask(UUID id, DailyTask updatedDailyTask) {
        return repository.findById(id)
            .map(existingDailyTask -> {
                existingDailyTask.setTitle(updatedDailyTask.getTitle());
                existingDailyTask.setDescription(updatedDailyTask.getDescription());
                existingDailyTask.setDifficulty(updatedDailyTask.getDifficulty());
                existingDailyTask.setPoints(calculatePoints(updatedDailyTask.getDifficulty()));
                existingDailyTask.setStartDate(updatedDailyTask.getStartDate());
                existingDailyTask.setRecurrenceType(updatedDailyTask.getRecurrenceType());
                existingDailyTask.setRepeatEvery(updatedDailyTask.getRepeatEvery());
                existingDailyTask.setDaysOfWeek(updatedDailyTask.getDaysOfWeek());
                return repository.save(existingDailyTask);
            })
            .orElseThrow(() -> new RuntimeException("DailyTask not found with id " + id));
    }

    public void deleteDailyTask(UUID id) {
        repository.deleteById(id);
    }

    public DailyTask completeDailyTask(UUID id) {
        DailyTask dailyTask = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyTask not found with id " + id));

        if (dailyTask.isDoneToday()) {
            throw new RuntimeException("DailyTask already completed");
        }

        dailyTask.setDoneToday(true);
        userService.addExperience(dailyTask.getPoints());
        dailyTask.setLastCompletedDate(LocalDate.now());
        return repository.save(dailyTask);
    }

    private int calculatePoints(TaskDifficulty difficulty) {
        switch (difficulty) {
            case FACIL: return 10;
            case NORMAL: return 20;
            case DIFICIL: return 40;
            case MUY_DIFICIL: return 60;
            default: return 0;
        }
    }

    public void refreshDailyTasks(JpaUserEntity user) {
        List<DailyTask> dailyTasks = repository.findByUser(user);
        LocalDate today = LocalDate.now();

        for (DailyTask dailyTask : dailyTasks) {
            if (shouldResetTask(dailyTask, today)) {
                dailyTask.setDoneToday(false);
                repository.save(dailyTask);
            }
        }
    }

    private boolean shouldResetTask(DailyTask dailyTask, LocalDate today) {
        if (dailyTask.getStartDate().isAfter(today)) {
            return false; // aún no ha empezado
        }

        // Nunca completada antes → debería estar activa hoy
        if (dailyTask.getLastCompletedDate() == null) {
            return true;
        }

        switch (dailyTask.getRecurrenceType()) {
            case DIARIO:
                // Ejemplo: repeatEvery = 2 → solo se activa cada 2 días
                return !today.equals(dailyTask.getLastCompletedDate())
                    && (daysBetween(dailyTask.getStartDate(), today) % dailyTask.getRepeatEvery() == 0);

            case SEMANAL:
                return !today.equals(dailyTask.getLastCompletedDate())
                    && dailyTask.getDaysOfWeek().contains(today.getDayOfWeek())
                    && (weeksBetween(dailyTask.getStartDate(), today) % dailyTask.getRepeatEvery() == 0);

            case MENSUAL:
                return !today.equals(dailyTask.getLastCompletedDate())
                    && today.getDayOfMonth() == dailyTask.getStartDate().getDayOfMonth()
                    && (monthsBetween(dailyTask.getStartDate(), today) % dailyTask.getRepeatEvery() == 0);

            case ANUAL:
                return !today.equals(dailyTask.getLastCompletedDate())
                    && today.getDayOfYear() == dailyTask.getStartDate().getDayOfYear()
                    && (yearsBetween(dailyTask.getStartDate(), today) % dailyTask.getRepeatEvery() == 0);

            default:
                return false;
        }
    }

    // Helpers
    private long daysBetween(LocalDate start, LocalDate end) {
        return java.time.temporal.ChronoUnit.DAYS.between(start, end);
    }

    private long weeksBetween(LocalDate start, LocalDate end) {
        return java.time.temporal.ChronoUnit.WEEKS.between(start, end);
    }

    private long monthsBetween(LocalDate start, LocalDate end) {
        return java.time.temporal.ChronoUnit.MONTHS.between(start, end);
    }

    private long yearsBetween(LocalDate start, LocalDate end) {
        return java.time.temporal.ChronoUnit.YEARS.between(start, end);
    }
}
