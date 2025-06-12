public class CompoundMonthlyInvestmentSummary implements InvestmentSummary {
	private final int principal;

	public CompoundMonthlyInvestmentSummary(int principal) {
		this.principal = principal;
	}

	@Override
	public int getPrincipal() {
		return this.principal;
	}
}
