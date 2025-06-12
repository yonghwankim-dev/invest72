public class CompoundMonthlyInvestmentCalculator implements InvestmentCalculator {
	@Override
	public int calculate(int monthlyInvestment, int investmentPeriod, double annualInterestRate) {
		return monthlyInvestment * investmentPeriod;
	}
}
