public interface Investment {
	InvestmentSummary calculate(int monthlyInvestment, InvestPeriod investPeriod, double annualInterestRate);
}
