package application.response;

import java.time.LocalDate;
import java.util.Objects;

public class TargetAchievementResponse {
	private final LocalDate achievedDate;
	private final int principal;
	private final int interest;
	private final int tax;
	private final int afterTaxInterest;
	private final int totalProfit;

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

	@Override
	public String toString() {
		return "TargetAchievementResponse{" +
			"achievedDate=" + achievedDate +
			", principal=" + principal +
			", interest=" + interest +
			", tax=" + tax +
			", afterTaxInterest=" + afterTaxInterest +
			", totalProfit=" + totalProfit +
			'}';
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		TargetAchievementResponse response = (TargetAchievementResponse)object;
		return principal == response.principal && interest == response.interest && tax == response.tax
			&& afterTaxInterest == response.afterTaxInterest && totalProfit == response.totalProfit
			&& Objects.equals(achievedDate, response.achievedDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(achievedDate, principal, interest, tax, afterTaxInterest, totalProfit);
	}
}
