package application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class MonthlyTargetAchievementUseCaseTest {

	@Test
	void created() {
		TargetAchievementUseCase useCase = new MonthlyTargetAchievementUseCase();

		assertNotNull(useCase);
	}

	@Test
	void calTargetAchievement_shouldReturnLocalDate() {
		TargetAchievementUseCase useCase = new MonthlyTargetAchievementUseCase();

		LocalDate localDate = useCase.calTargetAchievement();

		assertEquals(null, localDate);
	}
}
