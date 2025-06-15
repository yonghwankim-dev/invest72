public interface Investment {
	Interest calculate(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate);
}
