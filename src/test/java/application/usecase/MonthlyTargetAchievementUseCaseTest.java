package application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.invocation.InvocationOnMock;

import application.response.TargetAchievementResponse;
import application.time.DateProvider;
import domain.amount.DefaultTargetAmount;
import domain.amount.MonthlyInvestmentAmount;
import domain.amount.TargetAmount;
import domain.amount.TargetAmountReachable;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;

class MonthlyTargetAchievementUseCaseTest {

	private TargetAchievementUseCase useCase;

	public static Stream<Arguments> monthlyInvestmentAmountSource() {
		int targetAmount = 10_000_000;
		int expectedPrincipal = 10_000_000;
		return Stream.of(
			Arguments.of(targetAmount, 1_000_000, LocalDate.of(2025, 10, 1), expectedPrincipal, 41_660, 6415, 35_245),
			Arguments.of(targetAmount, 2_000_000, LocalDate.of(2025, 5, 1), expectedPrincipal, 41_665, 6416, 35_249),
			Arguments.of(targetAmount, 10_000_000, LocalDate.of(2025, 1, 1), expectedPrincipal, 41_666, 6416, 35_250),
			Arguments.of(targetAmount, 11_000_000, LocalDate.of(2025, 1, 1), 11_000_000, 45_833, 7058, 38_775),
			Arguments.of(12_050_000, 1_000_000, LocalDate.of(2025, 12, 1), 12_000_000, 49_992, 7698, 42_294)
		);
	}

	@BeforeEach
	void setUp() {
		DateProvider dateProvider = mock(DateProvider.class);
		LocalDate localDate = LocalDate.of(2025, 1, 1);
		given(dateProvider.now())
			.willReturn(localDate);
		given(dateProvider.calAchieveDate(anyInt()))
			.willAnswer(InvocationOnMock::callRealMethod);
		useCase = new MonthlyTargetAchievementUseCase(dateProvider);
	}

	@ParameterizedTest
	@MethodSource(value = "monthlyInvestmentAmountSource")
	void calTargetAchievement_shouldReturnLocalDate(int targetAmountValue, int monthlyInvestmentAmount,
		LocalDate expectedDate, int expectedPrincipal, int expectedInterest, int expectedTax,
		int expectedAfterTaxInterest) {
		TargetAmountReachable monthlyInvestment = new MonthlyInvestmentAmount(monthlyInvestmentAmount);
		TargetAmount targetAmount = new DefaultTargetAmount(targetAmountValue);
		InterestRate interestRate = new AnnualInterestRate(0.05);
		Taxable taxable = new KoreanTaxableFactory().createStandardTax(new FixedTaxRate(0.154));

		TargetAchievementResponse response = useCase.calTargetAchievement(targetAmount, monthlyInvestment,
			interestRate, taxable);

		assertEquals(expectedDate, response.getAchievedDate());
		assertEquals(expectedPrincipal, response.getPrincipal());
		assertEquals(expectedInterest, response.getInterest());
		assertEquals(expectedTax, response.getTax());
		assertEquals(expectedAfterTaxInterest, response.getAfterTaxInterest());
	}
}
