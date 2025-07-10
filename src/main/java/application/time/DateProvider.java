package application.time;

import java.time.LocalDate;

public interface DateProvider {
	default LocalDate now() {
		return LocalDate.now();
	}

	// -1을 빼는 이유는 현재 월을 포함하기 위해서입니다.
	default LocalDate calAchieveDate(int months) {
		return now().plusMonths(months - 1);
	}
}
