package co.invest72.achievement.domain;

import java.time.LocalDate;

public interface AchievementDateCalculator {
	default LocalDate now() {
		return LocalDate.now();
	}

	// -1을 빼는 이유는 현재 월을 포함하기 위해서입니다.
	default LocalDate addMonth(int month) {
		return now().plusMonths(month - 1);
	}
}
