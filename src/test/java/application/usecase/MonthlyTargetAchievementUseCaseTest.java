package application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.time.DateProvider;

class MonthlyTargetAchievementUseCaseTest {

	private DateProvider dateProvider;

	@BeforeEach
	void setUp() {
		dateProvider = mock(DateProvider.class);
		given(dateProvider.now())
			.willReturn(LocalDate.of(2025, 1, 1));
	}

	@Test
	void created() {
		TargetAchievementUseCase useCase = new MonthlyTargetAchievementUseCase(dateProvider);

		assertNotNull(useCase);
	}

	@Test
	void calTargetAchievement_shouldReturnLocalDate() {
		TargetAchievementUseCase useCase = new MonthlyTargetAchievementUseCase(dateProvider);

		LocalDate localDate = useCase.calTargetAchievement();

		LocalDate expectedDate = LocalDate.of(2025, 11, 1);
		assertEquals(expectedDate, localDate);
	}
}
