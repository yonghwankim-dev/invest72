public interface Investment {
	InvestmentSummary calculate(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate);
}
