public class YearlyInvestPeriod implements InvestPeriod {

	private final int years;

	public YearlyInvestPeriod(int years) {
		this.years = years;
	}

	@Override
	public int getMonths() {
		return years * 12;
	}

	@Override
	public int getTotalPrincipal(InvestmentAmount investmentAmount) {
		return 0;
	}

	@Override
	public double getYearsInvested(int currentMonth) {
		return 0;
	}
}
