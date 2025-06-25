package domain.type;

public enum PeriodType {
	MONTH("월"),
	YEAR("년");

	private final String displayName;

	PeriodType(String displayName) {
		this.displayName = displayName;
	}

	public static PeriodType from(String text) {
		if (text == null || text.isBlank()) {
			throw new IllegalArgumentException("text cannot be null or blank");
		}

		for (PeriodType periodType : values()) {
			if (periodType.displayName.equalsIgnoreCase(text.trim())) {
				return periodType;
			}
		}

		throw new IllegalArgumentException("Invalid period type: " + text);
	}

	public String getDisplayName() {
		return displayName;
	}
}
