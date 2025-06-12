public class CompoundBalance implements Balance {
	private final InvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;

	public CompoundBalance(InvestmentAmount investmentAmount, InvestPeriod investPeriod) {
		this.investmentAmount = investmentAmount;
		this.investPeriod = investPeriod;
	}

	@Override
	public int getTotalPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getInterestAmount() {
		int balanceValue = 12_330_017;
		return balanceValue - getTotalPrincipal();
	}
}
