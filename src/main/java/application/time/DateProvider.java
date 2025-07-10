package application.time;

import java.time.LocalDate;

public interface DateProvider {
	default LocalDate now() {
		return LocalDate.now();
	}

	default LocalDate calAchieveDate(int months) {
		return now().plusMonths(months);
	}
}
