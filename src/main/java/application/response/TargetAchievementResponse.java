package application.response;

import java.time.LocalDate;

public class TargetAchievementResponse {
	private final LocalDate achievedDate;
	private final int principal;
	private final int interest;
	private final int tax;

	public TargetAchievementResponse(LocalDate achievedDate, int principal, int interest, int tax) {
		this.achievedDate = achievedDate;
		this.principal = principal;
		this.interest = interest;
		this.tax = tax;
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
}
