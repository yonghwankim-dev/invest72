package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import application.request.TargetAchievementRequest;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.TaxType;

class TargetAchievementInvestmentCalculatorTest {

	@Test
	void calMonth() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		InvestmentCalculator calculator = new TargetAchievementInvestmentCalculator(taxableResolver);
		TargetAchievementRequest request = TargetAchievementRequest.builder()
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
