public class MonthlyInvestmentAmount implements InvestmentAmount {

	private final int amount;

	public MonthlyInvestmentAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public int getAmount() {
		return this.amount;
	}
}
