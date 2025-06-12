public class CompoundBalance implements Balance {
	private final InvestmentAmount investmentAmount;

	public CompoundBalance(InvestmentAmount investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	@Override
	public int getTotalPrincipal() {
		return investmentAmount.getAmount() * 12;
	}
}
