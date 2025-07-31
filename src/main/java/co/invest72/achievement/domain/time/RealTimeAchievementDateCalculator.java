package co.invest72.achievement.domain.time;

import java.time.LocalDate;

import co.invest72.achievement.domain.AchievementDateCalculator;

public class RealTimeAchievementDateCalculator implements AchievementDateCalculator {
	@Override
	public LocalDate now() {
		return AchievementDateCalculator.super.now();
	}

	@Override
	public LocalDate addMonth(int month) {
		return AchievementDateCalculator.super.addMonth(month);
	}
}
