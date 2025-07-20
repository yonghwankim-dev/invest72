package domain.invest_period;

public class PeriodYearRange implements PeriodRange {

	private final int years;

	public PeriodYearRange(int years) {
		this.years = years;
		if (this.years < 0) {
			throw new IllegalArgumentException("investment.ExpirationInvestment period must be greater than zero.");
		}
		if (this.years > 999) {
			throw new IllegalArgumentException(
				"investment.ExpirationInvestment period must not be greater than 999 months.");
		}
	}

	@Override
	public int toMonths() {
		return years * 12;
	}
}
