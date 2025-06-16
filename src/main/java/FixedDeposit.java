/**
 * 정기 예금
 */
public class FixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final Taxable taxable;

	public FixedDeposit(LumpSumInvestmentAmount investmentAmount, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.taxable = taxable;
	}

	@Override
	public int getAmount() {
		// 원금 + 이자 - 세금
		int prefixInterest = getPrefixInterest();
		int tax = getTax(prefixInterest);
		return investmentAmount.getDepositAmount() + prefixInterest - tax;
	}

	// todo: 이자 계산 구현
	private int getPrefixInterest() {
		return 51_162;
	}

	private int getTax(int prefixInterest) {
		return taxable.applyTax(prefixInterest);
	}
}
