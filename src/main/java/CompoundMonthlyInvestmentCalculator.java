public class CompoundMonthlyInvestmentCalculator implements InvestmentCalculator {
	@Override
	public InvestmentSummary calculate(int monthlyInvestment, int investmentPeriod, double annualInterestRate) {
		int principal = monthlyInvestment * investmentPeriod;
		int interest = (int)(principal * annualInterestRate);
		return new CompoundMonthlyInvestmentSummary(principal, interest);
	}
}
