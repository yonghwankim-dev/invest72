public class CompoundMonthlyInvestmentSummary implements InvestmentSummary {
	private final int principal;
	private final int interest;

	public CompoundMonthlyInvestmentSummary(int principal, int interest) {
		this.principal = principal;
		this.interest = interest;
	}

	@Override
	public int getPrincipal() {
		return this.principal;
	}

	@Override
	public int getInterest() {
		return this.interest;
	}
}
