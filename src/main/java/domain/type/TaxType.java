package domain.type;

public enum TaxType {
	STANDARD("읿반과세"),
	NON_TAX("비과세"),
	TAX_BENEFIT("세금우대");

	private final String description;

	TaxType(String description) {
		this.description = description;
	}

	public static TaxType from(String description) {
		for (TaxType taxType : values()) {
			if (taxType.description.equalsIgnoreCase(description)) {
				return taxType;
			}
		}
		throw new IllegalArgumentException("Invalid tax type: " + description);
	}
}
