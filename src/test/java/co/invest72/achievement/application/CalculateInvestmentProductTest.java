package co.invest72.achievement.application;

import static co.invest72.achievement.application.CalculateInvestmentProduct.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.application.InvestmentFactory;
import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.product.domain.AmountType;
import co.invest72.product.domain.InvestmentProductEntity;

class CalculateInvestmentProductTest {

	private CalculateInvestmentProduct useCase;

	@BeforeEach
	void setUp() {
		InvestmentFactory investmentFactory = new InvestmentFactory();
		useCase = new CalculateInvestmentProduct(investmentFactory);
	}

	@Test
	void calculate() {
		InvestmentProductEntity product = InvestmentProductEntity.builder()
			.id(1L)
			.uid("test-uid")
			.investmentType(InvestmentType.FIXED_DEPOSIT)
			.amountType(AmountType.ONE_TIME)
			.investmentAmount(1_000_000)
			.interestType(InterestType.SIMPLE)
			.annualRate(0.05)
			.investmentPeriodMonth(12)
			.taxType(TaxType.NON_TAX)
			.taxRate(0.0)
			.startDate(LocalDate.of(2024, 1, 1))
			.build();
		int targetAmount = 1_050_000;

		CalculateInvestmentProductResponse response = useCase.calculate(product, targetAmount);

		CalculateInvestmentProductResponse expected = new CalculateInvestmentProductResponse(LocalDate.of(2025, 1, 1),
			1_050_000L);
		Assertions.assertEquals(expected, response);
	}

	@Test
	void calculate_shouldThrowException_whenProductIsNotSatisfiedTargetAmount() {
		InvestmentProductEntity product = InvestmentProductEntity.builder()
			.id(1L)
			.uid("test-uid")
			.investmentType(InvestmentType.FIXED_DEPOSIT)
			.amountType(AmountType.ONE_TIME)
			.investmentAmount(1_000_000)
			.interestType(InterestType.SIMPLE)
			.annualRate(0.05)
			.investmentPeriodMonth(12)
			.taxType(TaxType.NON_TAX)
			.taxRate(0.0)
			.startDate(LocalDate.of(2024, 1, 1))
			.build();
		int targetAmount = 2_000_000;

		Assertions.assertThrows(IllegalArgumentException.class, () -> useCase.calculate(product, targetAmount));
	}

	@Test
	void calculate_whenProductIsCompoundFixedDeposit() {
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
			.startDate(LocalDate.of(2024, 1, 1))
			.build();
		int targetAmount = 1_050_000;

		CalculateInvestmentProductResponse response = useCase.calculate(product, targetAmount);

		CalculateInvestmentProductResponse expected = new CalculateInvestmentProductResponse(LocalDate.of(2025, 1, 1),
			1_051_162);
		Assertions.assertEquals(expected, response);
	}

	@Test
	void calculate_whenProductIsCompoundFixedInstallmentSaving() {
		InvestmentProductEntity product = InvestmentProductEntity.builder()
			.id(1L)
			.uid("test-uid")
			.investmentType(InvestmentType.INSTALLMENT_SAVING)
			.amountType(AmountType.MONTHLY)
			.investmentAmount(500_000)
			.interestType(InterestType.COMPOUND)
			.annualRate(0.05)
			.investmentPeriodMonth(24)
			.taxType(TaxType.NON_TAX)
			.taxRate(0.0)
			.startDate(LocalDate.of(2024, 1, 1))
			.build();
		int targetAmount = 10_449_265;

		CalculateInvestmentProductResponse response = useCase.calculate(product, targetAmount);

		CalculateInvestmentProductResponse expected = new CalculateInvestmentProductResponse(LocalDate.of(2025, 9, 1),
			10_449_265);
		Assertions.assertEquals(expected, response);
	}
}
