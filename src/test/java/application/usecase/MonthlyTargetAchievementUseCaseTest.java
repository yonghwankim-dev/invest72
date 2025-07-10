package application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.time.DateProvider;

class MonthlyTargetAchievementUseCaseTest {

	private TargetAchievementUseCase useCase;

	@BeforeEach
	void setUp() {
		DateProvider dateProvider = mock(DateProvider.class);
		given(dateProvider.now())
			.willReturn(LocalDate.of(2025, 1, 1));
		useCase = new MonthlyTargetAchievementUseCase(dateProvider);
	}

	@Test
	void calTargetAchievement_shouldReturnLocalDate() {
		int monthlyInvestmentAmount = 1_000_000;
		LocalDate localDate = useCase.calTargetAchievement(monthlyInvestmentAmount);

		assertEquals(LocalDate.of(2025, 11, 1), localDate);
	}
}
