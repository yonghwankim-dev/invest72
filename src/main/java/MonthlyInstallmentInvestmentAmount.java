public class MonthlyInstallmentInvestmentAmount implements InstallmentInvestmentAmount {

	private final int amount;

	public MonthlyInstallmentInvestmentAmount(int amount) {
		this.amount = amount;
		if (amount < 0) {
			throw new IllegalArgumentException("Investment amount must be non-negative.");
		}
	}

	@Override
	public int getMonthlyAmount() {
		return this.amount;
	}

	@Override
	public double calAnnualInterest(InterestRate interestRate) {
		return interestRate.getAnnualInterest(amount);
	}
}
