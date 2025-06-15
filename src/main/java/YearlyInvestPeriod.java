public class YearlyInvestPeriod implements InvestPeriod {

	private final int years;

	public YearlyInvestPeriod(int years) {
		this.years = years;
		if (this.years < 0) {
			throw new IllegalArgumentException("Investment period must be greater than zero.");
		}
		if (this.years > 999){
			throw new IllegalArgumentException("Investment period must not be greater than 999 months.");
		}
	}

	@Override
	public int getMonths() {
		return years * 12;
	}

	@Override
	public int getTotalPrincipal(InvestmentAmount investmentAmount) {
		return investmentAmount.getMonthlyAmount() * getMonths();
	}

	@Override
	public double getYearsInvested(int currentMonth) {
		return (getMonths() - currentMonth) / 12.0;
	}
}
