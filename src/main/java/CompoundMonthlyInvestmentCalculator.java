public class CompoundMonthlyInvestmentCalculator implements InvestmentCalculator {
	@Override
	public InvestmentSummary calculate(int monthlyInvestment, int investmentPeriod, double annualInterestRate) {
		int principal = monthlyInvestment * investmentPeriod;
		return new CompoundMonthlyInvestmentSummary(principal);
	}
}
