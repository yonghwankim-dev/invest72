package application.response;

import java.time.LocalDate;

public class TargetAchievementResponse {
	private final LocalDate achievedDate;
	private final int principal;

	public TargetAchievementResponse(LocalDate achievedDate, int principal) {
		this.achievedDate = achievedDate;
		this.principal = principal;
	}

	public LocalDate getAchievedDate() {
		return achievedDate;
	}

	public int getPrincipal() {
		return principal;
	}
}
