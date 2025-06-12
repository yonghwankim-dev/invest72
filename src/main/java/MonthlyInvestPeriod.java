public class MonthlyInvestPeriod implements InvestPeriod {
	private final int months;

	public MonthlyInvestPeriod(int months) {
		this.months = months;
		if (this.months < 0) {
			throw new IllegalArgumentException("Investment period must be greater than zero.");
		}
		if (this.months > 999){
			throw new IllegalArgumentException("Investment period must not be greater than 999 months.");
		}
	}

	@Override
	public int getMonths() {
		return months;
	}

	@Override
	public int getTotalPrincipal(int monthlyInvestment) {
		return monthlyInvestment * months;
	}
}
