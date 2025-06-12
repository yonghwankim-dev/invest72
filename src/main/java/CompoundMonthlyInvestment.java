public class CompoundMonthlyInvestment implements Investment {
	@Override
	public Balance calculate(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate) {
		return new CompoundBalance(investmentAmount, investPeriod, interestRate);
	}
}
