package application.response;

import java.time.LocalDate;

public class TargetAchievementResponse {
	private final LocalDate achievedDate;

	public TargetAchievementResponse(LocalDate achievedDate) {
		this.achievedDate = achievedDate;
	}

	public LocalDate getAchievedDate() {
		return achievedDate;
	}
}
