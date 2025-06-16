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
		// todo: 정기 예금 계산 로직을 구현
		// 원금 + 이자 - 세금
		int prefixInterest = getPrefixInterest();
		int tax = taxable.applyTax(prefixInterest);
		return investmentAmount.getDepositAmount() + prefixInterest - tax;
	}

	private int getPrefixInterest() {
		return 51_162;
	}
}
