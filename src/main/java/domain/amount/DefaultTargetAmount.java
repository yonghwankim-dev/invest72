package domain.amount;

public class DefaultTargetAmount implements TargetAmount {
	private final int amount;

	public DefaultTargetAmount(int amount) {
		this.amount = amount;
		if (amount <= 0) {
			throw new IllegalArgumentException("목표 금액은 0보다 커야 합니다.");
		}
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return "목표 금액: " + amount + "원";
	}
}
