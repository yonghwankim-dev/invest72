/**
 * 정기적금
 */
public class FixedInstallmentSaving implements Investment {

	private final Interest interest;

	public FixedInstallmentSaving(Interest interest) {
		this.interest = interest;
	}

	@Override
	public int getAmount() {
		return interest.getAmount();
	}
}
