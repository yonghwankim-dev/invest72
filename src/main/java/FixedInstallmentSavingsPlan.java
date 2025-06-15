/**
 * 정기적금
 */
public class FixedInstallmentSavingsPlan implements Investment {

	private final Interest interest;

	public FixedInstallmentSavingsPlan(Interest interest) {
		this.interest = interest;
	}

	@Override
	public int getAmount() {
		return interest.getAmount();
	}
}
