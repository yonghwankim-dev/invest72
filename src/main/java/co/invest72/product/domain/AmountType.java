package co.invest72.product.domain;

public enum AmountType {
	ONE_TIME("일시불"),
	MONTHLY("월"),
	YEARLY("년");

	private final String description;

	AmountType(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return String.format("AmountType{description='%s'}", description);
	}
}
