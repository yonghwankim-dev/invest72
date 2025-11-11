package co.invest72.investment.domain.period;

import co.invest72.investment.domain.PeriodRange;

public class PeriodYearRange implements PeriodRange {

	private final int years;

	public PeriodYearRange(int years) {
		this.years = years;
		if (this.years < 0) {
			throw new IllegalArgumentException("investment.Investment period must be greater than zero.");
		}
	}

	@Override
	public int toMonths() {
		return years * 12;
	}
}
