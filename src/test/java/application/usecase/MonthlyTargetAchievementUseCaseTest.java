package application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import application.response.TargetAchievementResponse;
import application.time.DateProvider;
import domain.amount.DefaultTargetAmount;
import domain.amount.MonthlyInvestmentAmount;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;

class MonthlyTargetAchievementUseCaseTest {

	private TargetAchievementUseCase useCase;

	public static Stream<Arguments> monthlyInvestmentAmountSource() {
		int targetAmount = 10_000_000;
		int expectedPrincipal = 10_000_000;
		return Stream.of(
			Arguments.of(targetAmount, 1_000_000, LocalDate.of(2025, 10, 1), expectedPrincipal),
			Arguments.of(targetAmount, 2_000_000, LocalDate.of(2025, 5, 1), expectedPrincipal),
			Arguments.of(targetAmount, 10_000_000, LocalDate.of(2025, 1, 1), expectedPrincipal),
			Arguments.of(targetAmount, 11_000_000, LocalDate.of(2025, 1, 1), 11_000_000),
			Arguments.of(12_050_000, 1_000_000, LocalDate.of(2025, 12, 1), 12_000_000)
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
		LocalDate expectedDate, int expectedPrincipal) {
		TargetAmountReachable monthlyInvestment = new MonthlyInvestmentAmount(monthlyInvestmentAmount);
		TargetAmount targetAmount = new DefaultTargetAmount(targetAmountValue);
		InterestRate interestRate = new AnnualInterestRate(0.05);

		TargetAchievementResponse response = useCase.calTargetAchievement(targetAmount, monthlyInvestment,
			interestRate);

		TargetAchievementResponse expected = new TargetAchievementResponse(expectedDate, expectedPrincipal);
		assertEquals(expected.getAchievedDate(), response.getAchievedDate());
		assertEquals(expected.getPrincipal(), response.getPrincipal());
	}
}
