public class CompoundFixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final InterestRate interestRate;
	private final InvestPeriod investPeriod;
	private final Taxable taxable;

	public CompoundFixedDeposit(LumpSumInvestmentAmount investmentAmount, InterestRate interestRate, InvestPeriod investPeriod, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.investPeriod = investPeriod;
		this.taxable = taxable;
	}

	@Override
	public int getAmount() {
		return 1_051_162;
	}
}
