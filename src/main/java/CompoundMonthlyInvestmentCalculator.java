public class CompoundMonthlyInvestmentCalculator implements InvestmentCalculator {
	@Override
	public int calculate(int monthlyInvestment, int investmentPeriod) {
		return monthlyInvestment * investmentPeriod;
	}
}
