public class FixedDepositAmount implements LumpSumInvestmentAmount {

	private final int amount;

	public FixedDepositAmount(int amount) {
		this.amount = amount;
		if (amount < 0) {
			throw new IllegalArgumentException("Investment amount must be non-negative.");
		}
	}

	@Override
	public int getDepositAmount() {
		return amount;
	}

	@Override
	public int calInterest(double totalGrowthFactor) {
		return (int)(Math.round(amount * totalGrowthFactor) - amount);
	}

	@Override
	public double getAnnualInterest(InterestRate interestRate) {
		return interestRate.getAnnualInterest(amount);
	}
}
