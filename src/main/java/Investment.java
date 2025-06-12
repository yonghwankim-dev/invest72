public interface Investment {
	InvestmentSummary calculate(int monthlyInvestment, int investmentPeriod, double annualInterestRate);

	InvestmentSummary calculate(int monthlyInvestment, InvestPeriod investPeriod, double annualInterestRate);
}
