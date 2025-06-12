public class CompoundMonthlyInvestment implements Investment {
	@Override
	public InvestmentSummary calculate(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate) {
		double balance = getBalance(investmentAmount, investPeriod, interestRate);
		int principal = investPeriod.getTotalPrincipal(investmentAmount);
		int interestAmount = getInterest(balance, principal);
		return new CompoundMonthlyInvestmentSummary(principal, interestAmount);
	}

	private double getBalance(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate) {
		double balance = 0;
		for (int i = 0; i < investPeriod.getMonths(); i++){
			balance += investmentAmount.getAmount();
			balance *= getGrowthFactor(interestRate);
		}
		return balance;
	}

	private double getGrowthFactor(InterestRate interestRate) {
		return 1 + interestRate.getMonthlyRate();
	}

	private int getInterest(double balance, double totalPrincipal) {
		return (int)(balance - totalPrincipal);
	}
}
