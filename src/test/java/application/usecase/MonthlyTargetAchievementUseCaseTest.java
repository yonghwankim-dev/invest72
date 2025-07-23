package application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import application.request.TargetAchievementRequest;
import application.response.TargetAchievementResponse;
import application.time.DateProvider;
import domain.type.TaxType;

class MonthlyTargetAchievementUseCaseTest {

	private TargetAchievementUseCase useCase;

	public static Stream<Arguments> monthlyInvestmentAmountSource() {
		return Stream.of(
			Arguments.of(10_000_000, 1_000_000, LocalDate.of(2025, 10, 1), 10_000_000, 41_666, 6_416, 35_250,
				10_035_250),
			Arguments.of(10_000_000, 2_000_000, LocalDate.of(2025, 5, 1), 10_000_000, 41_666, 6_416, 35_250,
				10_035_250),
			Arguments.of(10_000_000, 10_000_000, LocalDate.of(2025, 1, 1), 10_000_000, 41_666, 6416, 35_250,
				10_035_250),
			Arguments.of(10_000_000, 11_000_000, LocalDate.of(2025, 1, 1), 11_000_000, 45_833, 7058, 38_775,
				11_038_775),
			Arguments.of(12_050_000, 1_000_000, LocalDate.of(2025, 12, 1), 12_000_000, 50_000, 7_700, 42_300,
				12_042_300)
		);
	}

	private void assertTargetAchievementResponse(TargetAchievementResponse expected,
		TargetAchievementResponse response) {
		assertEquals(expected, response);
	}

	@BeforeEach
	void setUp() {
		DateProvider dateProvider = mock(DateProvider.class);
		LocalDate localDate = LocalDate.of(2025, 1, 1);
		given(dateProvider.now())
			.willReturn(localDate);
		given(dateProvider.calAchieveDate(anyInt()))
			.willCallRealMethod();
		useCase = new MonthlyTargetAchievementUseCase(dateProvider);
	}

	@ParameterizedTest
	@MethodSource(value = "monthlyInvestmentAmountSource")
	void calTargetAchievement(int targetAmountValue, int monthlyInvestmentAmount,
		LocalDate expectedDate, int expectedPrincipal, int expectedInterest, int expectedTax,
		int expectedAfterTaxInterest, int expectedTotalProfit) {
		double interestRate = 0.05;
		String taxType = TaxType.STANDARD.getDescription();
		double taxRate = 0.154;

		TargetAchievementRequest request = new TargetAchievementRequest(targetAmountValue, monthlyInvestmentAmount,
			interestRate, taxType, taxRate);
		TargetAchievementResponse response = useCase.calTargetAchievement(request);

		TargetAchievementResponse expected = TargetAchievementResponse.builder()
			.achievementDate(expectedDate)
			.principal(expectedPrincipal)
			.interest(expectedInterest)
			.tax(expectedTax)
			.afterTaxInterest(expectedAfterTaxInterest)
			.totalProfit(expectedTotalProfit)
			.build();
		assertTargetAchievementResponse(expected, response);
	}

	@Test
	void calTargetAchievement_givenInitialCapital() {
		int initialCapital = 1_000_000;
		int monthlyInvestmentAmount = 1_000_000;
		int targetAmount = 10_000_000;
		double interestRate = 0.05;
		String taxType = TaxType.STANDARD.getDescription();
		double taxRate = 0.154;

		TargetAchievementRequest request = new TargetAchievementRequest(initialCapital, targetAmount,
			monthlyInvestmentAmount, interestRate, taxType, taxRate);
		TargetAchievementResponse response = useCase.calTargetAchievement(request);

		TargetAchievementResponse expected = TargetAchievementResponse.builder()
			.achievementDate(LocalDate.of(2025, 9, 1))
			.principal(10_000_000)
			.interest(41_666)
			.tax(6_416)
			.afterTaxInterest(35_250)
			.totalProfit(10_035_250)
			.build();
		assertTargetAchievementResponse(expected, response);
	}
}
