public interface Investment {
	InvestmentSummary calculate(int monthlyInvestment, InvestPeriod investPeriod, double annualInterestRate);

	InvestmentSummary calculate(int monthlyInvestment, InvestPeriod investPeriod, Interest interest);
}
