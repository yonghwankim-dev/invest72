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
}
