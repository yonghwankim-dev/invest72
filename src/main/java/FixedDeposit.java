/**
 * 정기 예금
 */
public class FixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final InterestRate interestRate;
	private final InvestPeriod investPeriod;
	private final Taxable taxable;

	public FixedDeposit(LumpSumInvestmentAmount investmentAmount, InterestRate interestRate, InvestPeriod investPeriod, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.investPeriod = investPeriod;
		this.taxable = taxable;
	}

	@Override
	public int getAmount() {
		// 원금 + 이자 - 세금
		int prefixInterest = getPrefixInterest();
		int tax = getTax(prefixInterest);
		return investmentAmount.getDepositAmount() + prefixInterest - tax;
	}

	/**
	 * 단리 이자 계산
	 * 이자 = 투자금액 * 연이율 * 투자기간(년)
	 */
	private int getPrefixInterest() {
		return (int)(interestRate.getAnnualInterest(investmentAmount.getDepositAmount()) * investPeriod.getYearsInvested(0));
	}

	private int getTax(int prefixInterest) {
		return taxable.applyTax(prefixInterest);
	}
}
