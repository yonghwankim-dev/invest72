package application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import application.time.DateProvider;
import domain.amount.DefaultTargetAmount;
import domain.amount.MonthlyInvestmentAmount;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;

class MonthlyTargetAchievementUseCaseTest {

	private TargetAchievementUseCase useCase;
	private TargetAmountReachable monthlyInvestment;

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
	void calTargetAchievement_shouldReturnLocalDate(int targetAmountValue, int monthlyInvestmentAmount,
		LocalDate expectedDate) {
		monthlyInvestment = new MonthlyInvestmentAmount(monthlyInvestmentAmount);
		TargetAmount targetAmount = new DefaultTargetAmount(targetAmountValue);
		LocalDate localDate = useCase.calTargetAchievement(targetAmount, monthlyInvestment);

		assertEquals(expectedDate, localDate);
	}
}
