package application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonthlyTargetAchievementUseCaseTest {

	@Test
	void created() {
		TargetAchievementUseCase useCase = new MonthlyTargetAchievementUseCase();

		assertNotNull(useCase);
	}
}
