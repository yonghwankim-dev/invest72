public class YearlyInstallmentInvestmentAmount implements InstallmentInvestmentAmount {
	private final int amount;

	public YearlyInstallmentInvestmentAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int getMonthlyAmount() {
		return amount / 12;
	}

	@Override
	public double getAnnualInterest(InterestRate interestRate) {
		return interestRate.getAnnualInterest(amount);
	}
}
