/**
 * 정기 예금
 */
public class FixedDeposit implements Investment {

	private final int depositAmount;
	private final Taxable taxable;

	public FixedDeposit(int depositAmount, Taxable taxable) {
		this.depositAmount = depositAmount;
		this.taxable = taxable;
	}

	@Override
	public int getAmount() {
		// todo: 정기 예금 계산 로직을 구현
		// 원금 + 이자 - 세금
		int prefixInterest = getPrefixInterest();
		int tax = taxable.applyTax(prefixInterest);
		return depositAmount + prefixInterest - tax;
	}

	private int getPrefixInterest() {
		return 51_162;
	}
}
