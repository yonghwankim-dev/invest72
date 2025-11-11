package co.invest72.investment.domain.period;

import co.invest72.investment.domain.PeriodRange;

public class PeriodMonthsRange implements PeriodRange {

	private final int months;

	public PeriodMonthsRange(int months) {
		this.months = months;
		if (this.months < 0) {
			throw new IllegalArgumentException("Investment period must be greater than or equal to zero.");
		}
	}

	@Override
	public int toMonths() {
		return months;
	}
}
