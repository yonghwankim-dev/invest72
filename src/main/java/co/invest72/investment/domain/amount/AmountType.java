package co.invest72.investment.domain.amount;

public enum AmountType {
	MONTHLY("월"),
	YEARLY("년"),
	ONE_TIME("일시불");

	private final String description;

	AmountType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
