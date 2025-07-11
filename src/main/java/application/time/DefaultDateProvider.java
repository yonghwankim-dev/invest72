package application.time;

import java.time.LocalDate;

public class DefaultDateProvider implements DateProvider {
	@Override
	public LocalDate now() {
		return DateProvider.super.now();
	}

	@Override
	public LocalDate calAchieveDate(int months) {
		return DateProvider.super.calAchieveDate(months);
	}
}
