public interface Investment {
	InvestmentSummary calculate(int monthlyInvestment, InvestPeriod investPeriod, InterestRate interestRate);
}
