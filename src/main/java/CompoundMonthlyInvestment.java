public class CompoundMonthlyInvestment implements Investment {
	@Override
	public InvestmentSummary calculate(int monthlyInvestment, InvestPeriod investPeriod, double annualInterestRate) {
		if (monthlyInvestment < 0) {
			throw new IllegalArgumentException("Monthly investment must be non-negative.");
		}
		double monthlyRate = getMonthlyRate(annualInterestRate);

		double balance = getBalance(monthlyInvestment, investPeriod, monthlyRate);
		int principal = investPeriod.getTotalPrincipal(monthlyInvestment);
		int interest = getInterest(balance, principal);
		return new CompoundMonthlyInvestmentSummary(principal, interest);
	}

	private double getMonthlyRate(double annualInterestRate) {
		return annualInterestRate / 12;
	}

	private double getBalance(int monthlyInvestment, InvestPeriod investPeriod, double monthlyRate) {
		double balance = 0;
		for (int i = 0; i < investPeriod.getMonths(); i++){
			balance += monthlyInvestment;
			balance *= applyMonthlyRate(monthlyRate);
		}
		return balance;
	}

	private double applyMonthlyRate(double monthlyRate) {
		return 1 + monthlyRate;
	}

	private int getInterest(double balance, double totalPrincipal) {
		return (int)(balance - totalPrincipal);
	}

	@Override
	public InvestmentSummary calculate(int monthlyInvestment, InvestPeriod investPeriod, Interest interest) {
		return calculate(monthlyInvestment, investPeriod, interest.getAnnualRate());
	}
}
