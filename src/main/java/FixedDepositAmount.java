public class FixedDepositAmount implements LumpSumInvestmentAmount {

	private final int amount;

	public FixedDepositAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public double getAnnualInterest(InterestRate interestRate) {
		return interestRate.getAnnualInterest(amount);
	}

	@Override
	public int getDepositAmount() {
		return amount;
	}
}
