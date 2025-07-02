package domain.type;

public enum InvestmentType {
	FIXED_DEPOSIT("예금"),
	INSTALLMENT_SAVING("적금");

	private final String typeName;

	InvestmentType(String typeName) {
		this.typeName = typeName;
	}

	public static InvestmentType from(String type) {
		for (InvestmentType investmentType : values()) {
			if (investmentType.typeName.equalsIgnoreCase(type)) {
				return investmentType;
			}
		}
		throw new IllegalArgumentException("Unknown investment type: " + type);
	}

	public String getTypeName() {
		return typeName;
	}
}
