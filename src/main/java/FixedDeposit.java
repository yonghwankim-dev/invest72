/**
 * 정기 예금
 */
public class FixedDeposit implements Investment {

	private final InvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Interest interest;
	private final Taxable taxable;

	public FixedDeposit(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate,
		Interest interest, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.interest = interest;
		this.taxable = taxable;
	}

	@Override
	public int getAmount() {
		// todo: 정기 예금 계산 로직을 구현
		return 1_051_162;
	}
}
