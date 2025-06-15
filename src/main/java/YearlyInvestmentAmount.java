public class YearlyInvestmentAmount implements InvestmentAmount {
	private final int amount;

	public YearlyInvestmentAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int getAmount() {
		return amount / 12;
	}

	@Override
	public double getAnnualInterest(InterestRate interestRate) {
		return 0;
	}
}
