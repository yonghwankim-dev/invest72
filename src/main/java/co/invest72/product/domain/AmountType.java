package co.invest72.product.domain;

/**
 * Represents the type of amount for a product, such as one-time, monthly, or yearly payments.
 * This enum is used to distinguish between different payment or contribution frequencies.
 */
public enum AmountType {
	/**
	 * One-time payment (일시불).
	 * Used for payments made only once.
	 */
	ONE_TIME("일시불"),
	/**
	 * Monthly payment (월).
	 * Used for payments made every month.
	 */
	MONTHLY("월"),
	/**
	 * Yearly payment (년).
	 * Used for payments made every year.
	 */
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
