package co.invest72.investment.domain.period;

import co.invest72.investment.domain.PeriodRange;

public class PeriodMonthsRange implements PeriodRange {

	private final int months;

	public PeriodMonthsRange(int months) {
		this.months = months;
		if (this.months < 0) {
			throw new IllegalArgumentException("Investment period must be greater than or equal to zero.");
		}
		if (this.months > 999) {
			throw new IllegalArgumentException("Investment period must not be greater than 999 months.");
		}
	}

	@Override
	public int toMonths() {
		return months;
	}
}
