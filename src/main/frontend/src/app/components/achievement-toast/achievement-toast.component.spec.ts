import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AchievementToastComponent } from './achievement-toast.component';

describe('AchievementToastComponent', () => {
  let component: AchievementToastComponent;
  let fixture: ComponentFixture<AchievementToastComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AchievementToastComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AchievementToastComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
