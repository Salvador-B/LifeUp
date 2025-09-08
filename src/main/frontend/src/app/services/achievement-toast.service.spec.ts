import { TestBed } from '@angular/core/testing';

import { AchievementToastService } from './achievement-toast.service';

describe('AchievementToastService', () => {
  let service: AchievementToastService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AchievementToastService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
