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
