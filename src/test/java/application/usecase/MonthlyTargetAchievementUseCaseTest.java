package application.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import application.time.DateProvider;

class MonthlyTargetAchievementUseCaseTest {

	@Test
	void created() {
		DateProvider dateProvider = new DateProvider() {
			@Override
			public LocalDate now() {
				return DateProvider.super.now();
			}
		};
		TargetAchievementUseCase useCase = new MonthlyTargetAchievementUseCase(dateProvider);

		assertNotNull(useCase);
	}

	@Test
	void calTargetAchievement_shouldReturnLocalDate() {
		DateProvider dateProvider = new DateProvider() {
			@Override
			public LocalDate now() {
				return DateProvider.super.now();
			}
		};
		TargetAchievementUseCase useCase = new MonthlyTargetAchievementUseCase(dateProvider);

		LocalDate localDate = useCase.calTargetAchievement();

		LocalDate expectedDate = LocalDate.of(2026, 5, 10);
		assertEquals(expectedDate, localDate);
	}
}
