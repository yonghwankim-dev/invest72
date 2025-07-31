package co.invest72.achievement.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import co.invest72.achievement.application.CalculateAchievement;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.tax.TaxType;

class AchievementInvestmentCalculatorTest {

	@Test
	void calMonth() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		AchievementInvestmentCalculator calculator = new AchievementInvestmentCalculator(taxableResolver);
		CalculateAchievement.AchievementRequest request = CalculateAchievement.AchievementRequest.builder()
			.targetAmount(10000000)
			.monthlyInvestmentAmount(1000000)
			.interestRate(0.05)
			.taxType(TaxType.STANDARD.getDescription())
			.taxRate(0.154)
			.build();
		int month = calculator.calMonth(request);

		Assertions.assertEquals(10, month);
	}

}
