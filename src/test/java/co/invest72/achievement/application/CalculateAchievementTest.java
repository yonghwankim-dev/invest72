package co.invest72.achievement.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import co.invest72.achievement.domain.AchievementDateCalculator;
import co.invest72.achievement.domain.time.AchievementInvestmentCalculator;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.TaxableResolver;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.investment.domain.tax.resolver.KoreanStringBasedTaxableResolver;

class CalculateAchievementTest {

	private CalculateAchievement useCase;
	private AchievementDateCalculator achievementDateCalculator;

	private void assertTargetAchievementResponse(CalculateAchievement.AchievementResponse expected,
		CalculateAchievement.AchievementResponse response) {
		assertEquals(expected, response);
	}

	@BeforeEach
	void setUp() {
		achievementDateCalculator = mock(AchievementDateCalculator.class);
		given(achievementDateCalculator.addMonth(anyInt()))
			.willCallRealMethod();
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		AchievementInvestmentCalculator calculator = new AchievementInvestmentCalculator(taxableResolver);
		useCase = new CalculateAchievement(achievementDateCalculator, taxableResolver, calculator);
	}

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/target_achievement_case/monthly_target_achievement.csv", numLinesToSkip = 1)
	void calTargetAchievementFromCsv(LocalDate startDate, int targetAmount, int initialCapital,
		int monthlyInvestmentAmount, double interestRate, LocalDate expectedDate, int expectedPrincipal,
		int expectedInterest, int expectedTax, int expectedAfterTaxInterest, int expectedTotalProfit) {
		given(achievementDateCalculator.now()).willReturn(startDate);
		CalculateAchievement.AchievementRequest request = CalculateAchievement.AchievementRequest.builder()
			.initialCapital(initialCapital)
			.targetAmount(targetAmount)
			.monthlyInvestmentAmount(monthlyInvestmentAmount)
			.interestRate(interestRate)
			.taxType(TaxType.STANDARD.getDescription())
			.taxRate(0.154)
			.build();

		CalculateAchievement.AchievementResponse response = useCase.calAchievement(request);

		CalculateAchievement.AchievementResponse expected = CalculateAchievement.AchievementResponse.builder()
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
