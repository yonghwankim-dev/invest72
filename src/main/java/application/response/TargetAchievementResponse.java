package application.response;

import java.time.LocalDate;

public class TargetAchievementResponse {
	private final LocalDate achievedDate;
	private final int principal;
	private final int interest;

	public TargetAchievementResponse(LocalDate achievedDate, int principal, int interest) {
		this.achievedDate = achievedDate;
		this.principal = principal;
		this.interest = interest;
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
}
