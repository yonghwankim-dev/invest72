package co.invest72.investment.domain.period;

import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.PeriodRange;

public enum PeriodType {
	MONTH("월") {
		@Override
		public InvestPeriod create(PeriodRange periodRange) {
			return new MonthlyInvestPeriod(periodRange);
		}
	},
	YEAR("년") {
		@Override
		public InvestPeriod create(PeriodRange periodRange) {
			return new YearlyInvestPeriod(periodRange);
		}
	};

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

	public abstract InvestPeriod create(PeriodRange periodRange);

	public String getDisplayName() {
		return displayName;
	}
}
