package co.invest72.investment.domain.period;

import co.invest72.investment.domain.PeriodRange;
import co.invest72.investment.domain.RemainingPeriodProvider;

public class MonthBasedRemainingPeriodProvider implements RemainingPeriodProvider {

	private final PeriodRange periodRange;

	public MonthBasedRemainingPeriodProvider(PeriodRange periodRange) {
		this.periodRange = periodRange;
	}

	@Override
	public double calRemainingPeriodInYears(int currentMonth) {
		int months = periodRange.toMonths();
		if (currentMonth < 0 || currentMonth > months) {
			throw new IllegalArgumentException(
				"Current month must be between 0 and the total investment period in months.");
		}
		return (months - currentMonth) / 12.0;
	}

	@Override
	public int getFinalMonth() {
		return periodRange.toMonths();
	}
}
