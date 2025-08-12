package co.invest72.achievement.application;

import static co.invest72.achievement.application.CalculateInvestmentProduct.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.achievement.domain.time.AchievementInvestmentCalculator;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.TaxableResolver;
import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.investment.domain.tax.resolver.KoreanStringBasedTaxableResolver;
import co.invest72.product.domain.AmountType;
import co.invest72.product.domain.InvestmentProductEntity;

class CalculateInvestmentProductTest {

	private CalculateInvestmentProduct useCase;

	@BeforeEach
	void setUp() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		AchievementInvestmentCalculator calculator = new AchievementInvestmentCalculator(taxableResolver);
		useCase = new CalculateInvestmentProduct(calculator);
	}
	
	@Test
	void calculate() {
		InvestmentProductEntity product = InvestmentProductEntity.builder()
			.id(1L)
			.uid("test-uid")
			.investmentType(InvestmentType.FIXED_DEPOSIT)
			.amountType(AmountType.ONE_TIME)
			.investmentAmount(1_000_000)
			.interestType(InterestType.COMPOUND)
			.annualRate(0.05)
			.investmentPeriodMonth(12)
			.taxType(TaxType.NON_TAX)
			.taxRate(0.0)
			.build();

		CalculateInvestmentProductResponse response = useCase.calculate(product);

		CalculateInvestmentProductResponse expected = new CalculateInvestmentProductResponse(LocalDate.of(2025, 1, 1),
			1_050_000L);
		Assertions.assertEquals(expected, response);
	}
}
