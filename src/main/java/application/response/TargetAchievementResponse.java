package application.response;

import java.time.LocalDate;

public class TargetAchievementResponse {
	private final LocalDate achievedDate;
	private final int principal;
	private final int interest;
	private final int tax;
	private final int afterTaxInterest;
	private final int totalProfit;

	public TargetAchievementResponse(LocalDate achievedDate, int principal, int interest, int tax,
		int afterTaxInterest, int totalProfit) {
		this.achievedDate = achievedDate;
		this.principal = principal;
		this.interest = interest;
		this.tax = tax;
		this.afterTaxInterest = afterTaxInterest;
		this.totalProfit = totalProfit;
	}

	public static TargetAchievementResponseBuilder builder() {
		return new TargetAchievementResponseBuilder();
	}

	private TargetAchievementResponse(TargetAchievementResponseBuilder builder) {
		this.achievedDate = builder.achievedDate;
		this.principal = builder.principal;
		this.interest = builder.interest;
		this.tax = builder.tax;
		this.afterTaxInterest = builder.afterTaxInterest;
		this.totalProfit = builder.totalProfit;
	}

	public static class TargetAchievementResponseBuilder {
		private LocalDate achievedDate;
		private int principal;
		private int interest;
		private int tax;
		private int afterTaxInterest;
		private int totalProfit;

		public TargetAchievementResponseBuilder achievementDate(LocalDate achievedDate) {
			this.achievedDate = achievedDate;
			return this;
		}

		public TargetAchievementResponseBuilder principal(int principal) {
			this.principal = principal;
			return this;
		}

		public TargetAchievementResponseBuilder interest(int interest) {
			this.interest = interest;
			return this;
		}

		public TargetAchievementResponseBuilder tax(int tax) {
			this.tax = tax;
			return this;
		}

		public TargetAchievementResponseBuilder afterTaxInterest(int afterTaxInterest) {
			this.afterTaxInterest = afterTaxInterest;
			return this;
		}

		public TargetAchievementResponseBuilder totalProfit(int totalProfit) {
			this.totalProfit = totalProfit;
			return this;
		}

		public TargetAchievementResponse build() {
			return new TargetAchievementResponse(this);
		}
	}

	public LocalDate getAchievedDate() {
		return achievedDate;
	}

	public int getPrincipal() {
		return principal;
	}

	public int getInterest() {
		return interest;
	}

	public int getTax() {
		return tax;
	}

	public int getAfterTaxInterest() {
		return afterTaxInterest;
	}

	public int getTotalProfit() {
		return totalProfit;
	}
}
