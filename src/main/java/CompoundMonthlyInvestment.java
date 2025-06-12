public class CompoundMonthlyInvestment implements Investment {
	@Override
	public InvestmentSummary calculate(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate) {
		double monthlyRate = interestRate.getMonthlyRate();

		double balance = getBalance(investmentAmount, investPeriod, monthlyRate);
		int principal = investPeriod.getTotalPrincipal(investmentAmount);
		int interestAmount = getInterest(balance, principal);
		return new CompoundMonthlyInvestmentSummary(principal, interestAmount);
	}

	private double getBalance(InvestmentAmount investmentAmount, InvestPeriod investPeriod, double monthlyRate) {
		double balance = 0;
		for (int i = 0; i < investPeriod.getMonths(); i++){
			balance += investmentAmount.getAmount();
			balance *= getGrowthFactor(monthlyRate);
		}
		return balance;
	}

	private double getGrowthFactor(double monthlyRate) {
		return 1 + monthlyRate;
	}

	private int getInterest(double balance, double totalPrincipal) {
		return (int)(balance - totalPrincipal);
	}
}
