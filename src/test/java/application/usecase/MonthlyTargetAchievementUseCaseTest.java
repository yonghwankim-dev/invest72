package application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import application.request.TargetAchievementRequest;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import application.response.TargetAchievementResponse;
import application.time.DateProvider;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.TaxType;

class MonthlyTargetAchievementUseCaseTest {

	private TargetAchievementUseCase useCase;
	private DateProvider dateProvider;

	private void assertTargetAchievementResponse(TargetAchievementResponse expected,
		TargetAchievementResponse response) {
		assertEquals(expected, response);
	}

	@BeforeEach
	void setUp() {
		dateProvider = mock(DateProvider.class);
		given(dateProvider.calAchieveDate(anyInt()))
			.willCallRealMethod();
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		useCase = new MonthlyTargetAchievementUseCase(dateProvider, taxableResolver);
	}

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/target_achievement_case/monthly_target_achievement.csv", numLinesToSkip = 1)
	void calTargetAchievementFromCsv(LocalDate startDate, int targetAmount, int initialCapital,
		int monthlyInvestmentAmount, double interestRate, LocalDate expectedDate, int expectedPrincipal,
		int expectedInterest, int expectedTax, int expectedAfterTaxInterest, int expectedTotalProfit) {
		given(dateProvider.now()).willReturn(startDate);
		TargetAchievementRequest request = TargetAchievementRequest.builder()
			.initialCapital(initialCapital)
			.targetAmount(targetAmount)
			.monthlyInvestmentAmount(monthlyInvestmentAmount)
			.interestRate(interestRate)
			.taxType(TaxType.STANDARD.getDescription())
			.taxRate(0.154)
			.build();

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
}
