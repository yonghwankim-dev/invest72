package application.request;

public record TargetAchievementRequest(int initialCapital, int targetAmount,
									   int monthlyInvestmentAmount, double interestRate,
									   String taxType, double taxRate) {

	public TargetAchievementRequest(int targetAmount, int monthlyInvestmentAmount,
		double interestRate, String taxType, double taxRate) {
		this(0,
			targetAmount,
			monthlyInvestmentAmount,
			interestRate,
			taxType,
			taxRate
		);
	}

	public TargetAchievementRequest(TargetAchievementRequestBuilder builder) {
		this(builder.initialCapital,
			builder.targetAmount,
			builder.monthlyInvestmentAmount,
			builder.interestRate,
			builder.taxType,
			builder.taxRate
		);
	}

	public static TargetAchievementRequestBuilder builder() {
		return new TargetAchievementRequestBuilder();
	}

	public static class TargetAchievementRequestBuilder {
		private int initialCapital;
		private int targetAmount;
		private int monthlyInvestmentAmount;
		private double interestRate;
		private String taxType;
		private double taxRate;

		public TargetAchievementRequestBuilder initialCapital(int initialCapital) {
			this.initialCapital = initialCapital;
			return this;
		}

		public TargetAchievementRequestBuilder targetAmount(int targetAmount) {
			this.targetAmount = targetAmount;
			return this;
		}

		public TargetAchievementRequestBuilder monthlyInvestmentAmount(int monthlyInvestmentAmount) {
			this.monthlyInvestmentAmount = monthlyInvestmentAmount;
			return this;
		}

		public TargetAchievementRequestBuilder interestRate(double interestRate) {
			this.interestRate = interestRate;
			return this;
		}

		public TargetAchievementRequestBuilder taxType(String taxType) {
			this.taxType = taxType;
			return this;
		}

		public TargetAchievementRequestBuilder taxRate(double taxRate) {
			this.taxRate = taxRate;
			return this;
		}

		public TargetAchievementRequest build() {
			return new TargetAchievementRequest(this);
		}
	}
}
