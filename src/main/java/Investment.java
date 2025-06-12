public interface Investment {
	Balance calculate(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate);
}
