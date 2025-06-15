public class MonthlyInvestmentAmount implements InvestmentAmount {

	private final int amount;

	public MonthlyInvestmentAmount(int amount) {
		this.amount = amount;
		if (amount < 0) {
			throw new IllegalArgumentException("Investment amount must be non-negative.");
		}
	}

	@Override
	public int getAmount() {
		return this.amount;
	}

	@Override
	public double getAnnualInterest(InterestRate interestRate) {
		return interestRate.getAnnualInterest(amount);
	}
}
