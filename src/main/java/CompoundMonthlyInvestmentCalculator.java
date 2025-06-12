public class CompoundMonthlyInvestmentCalculator implements InvestmentCalculator {
	@Override
	public InvestmentSummary calculate(int monthlyInvestment, int investmentPeriod, double annualInterestRate) {
		if (monthlyInvestment < 0) {
			throw new IllegalArgumentException("Monthly investment must be non-negative.");
		}
		double monthlyRate = getMonthlyRate(annualInterestRate);
		double totalPrincipal = 0;
		double balance = 0;

		for (int i = 0; i < investmentPeriod; i++){
			balance += monthlyInvestment;
			balance *= applyMonthlyRate(monthlyRate);
			totalPrincipal += monthlyInvestment;
		}

		int principal = toInt(totalPrincipal);
		int interest = getInterest(balance, totalPrincipal);
		return new CompoundMonthlyInvestmentSummary(principal, interest);
	}

	private double getMonthlyRate(double annualInterestRate) {
		return annualInterestRate / 12;
	}

	private double applyMonthlyRate(double monthlyRate) {
		return 1 + monthlyRate;
	}

	private int toInt(double value) {
		return (int)Math.round(value);
	}

	private int getInterest(double balance, double totalPrincipal) {
		return (int)(balance - totalPrincipal);
	}
}
