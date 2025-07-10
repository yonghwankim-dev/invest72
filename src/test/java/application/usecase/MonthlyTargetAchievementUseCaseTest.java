package application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import application.time.DateProvider;

class MonthlyTargetAchievementUseCaseTest {

	private TargetAchievementUseCase useCase;

	public static Stream<Arguments> monthlyInvestmentAmountSource() {
		int targetAmount = 10_000_000;
		return Stream.of(
			Arguments.of(targetAmount, 1_000_000, LocalDate.of(2025, 10, 1)),
			Arguments.of(targetAmount, 2_000_000, LocalDate.of(2025, 5, 1)),
			Arguments.of(targetAmount, 10_000_000, LocalDate.of(2025, 1, 1)),
			Arguments.of(targetAmount, 11_000_000, LocalDate.of(2025, 1, 1))
		);
	}

	@BeforeEach
	void setUp() {
		DateProvider dateProvider = mock(DateProvider.class);
		given(dateProvider.now())
			.willReturn(LocalDate.of(2025, 1, 1));
		useCase = new MonthlyTargetAchievementUseCase(dateProvider);
	}

	@ParameterizedTest
	@MethodSource(value = "monthlyInvestmentAmountSource")
	void calTargetAchievement_shouldReturnLocalDate(int targetAmount, int monthlyInvestmentAmount,
		LocalDate expectedDate) {
		LocalDate localDate = useCase.calTargetAchievement(targetAmount, monthlyInvestmentAmount);

		assertEquals(expectedDate, localDate);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1, -1000})
	void calTargetAchievement_shouldThrowException_whenMonthlyInvestmentAmountIsInvalid(int monthlyInvestmentAmount) {
		int targetAmount = 10_000_000;

		assertThrows(IllegalArgumentException.class,
			() -> useCase.calTargetAchievement(targetAmount, monthlyInvestmentAmount));
	}
}
