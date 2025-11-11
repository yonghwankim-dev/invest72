package co.invest72.investment.application;

import static co.invest72.investment.application.CalculateMonthlyInvestment.*;
import static co.invest72.investment.domain.interest.InterestType.*;
import static co.invest72.investment.domain.investment.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.amount.AmountType;
import co.invest72.investment.domain.period.PeriodType;
import co.invest72.investment.domain.tax.TaxType;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;

class CalculateMonthlyInvestmentTest {

	private CalculateMonthlyInvestment usecase;

	@BeforeEach
	void setUp() {
		InvestmentFactory investmentFactory = new InvestmentFactory();
		usecase = new CalculateMonthlyInvestment(investmentFactory);
	}

	@DisplayName("월별 투자 금액 계산 - 고정 예금, 단리, 과세")
	@Test
	void calMonthlyInvestmentAmount_shouldReturnResponse() {
		CalculateInvestmentRequest request = CalculateInvestmentRequest.builder()
			.type(FIXED_DEPOSIT.getTypeName())
			.amountType(AmountType.ONE_TIME.getDescription())
			.amount(1_000_000)
			.periodType(PeriodType.MONTH.getDisplayName())
			.periodValue(4)
			.interestType(SIMPLE.getTypeName())
			.annualInterestRate(0.05)
			.taxType(TaxType.STANDARD.getDescription())
			.taxRate(0.154)
			.build();
		CalculateMonthlyInvestmentResponse response = usecase.calMonthlyInvestmentAmount(
			request);

		List<MonthlyInvestmentResult> monthlyInvestmentResults = List.of(
			new MonthlyInvestmentResult(1, 1_000_000, 4_167, 642, 1_003_525),
			new MonthlyInvestmentResult(2, 1_000_000, 8_333, 1_283, 1_007_050),
			new MonthlyInvestmentResult(3, 1_000_000, 12_500, 1_925, 1_010_575),
			new MonthlyInvestmentResult(4, 1_000_000, 16_667, 2_567, 1_014_100)
		);
		CalculateMonthlyInvestmentResponse expected = new CalculateMonthlyInvestmentResponse(
			monthlyInvestmentResults);
		assertEquals(expected, response);
	}

	@DisplayName("월별 투자 금액 계산 - 고정 예금, 단리, 비과세")
	@Test
	void calMonthlyInvestmentAmount_shouldSimpleFixedDeposit() {
		CalculateInvestmentRequest request = CalculateInvestmentRequest.builder()
			.type(FIXED_DEPOSIT.getTypeName())
			.amountType(AmountType.ONE_TIME.getDescription())
			.amount(1_000_000)
			.periodType(PeriodType.MONTH.getDisplayName())
			.periodValue(12)
			.interestType(SIMPLE.getTypeName())
			.annualInterestRate(0.05)
			.taxType(TaxType.NON_TAX.getDescription())
			.taxRate(0.0)
			.build();
		CalculateMonthlyInvestmentResponse response = usecase.calMonthlyInvestmentAmount(
			request);

		List<MonthlyInvestmentResult> monthlyInvestmentResults = List.of(
			new MonthlyInvestmentResult(1, 1_000_000, 4_167, 0, 1_004_167),
			new MonthlyInvestmentResult(2, 1_000_000, 8_333, 0, 1_008_333),
			new MonthlyInvestmentResult(3, 1_000_000, 12_500, 0, 1_012_500),
			new MonthlyInvestmentResult(4, 1_000_000, 16_667, 0, 1_016_667),
			new MonthlyInvestmentResult(5, 1_000_000, 20_833, 0, 1_020_833),
			new MonthlyInvestmentResult(6, 1_000_000, 25_000, 0, 1_025_000),
			new MonthlyInvestmentResult(7, 1_000_000, 29_167, 0, 1_029_167),
			new MonthlyInvestmentResult(8, 1_000_000, 33_333, 0, 1_033_333),
			new MonthlyInvestmentResult(9, 1_000_000, 37_500, 0, 1_037_500),
			new MonthlyInvestmentResult(10, 1_000_000, 41_667, 0, 1_041_667),
			new MonthlyInvestmentResult(11, 1_000_000, 45_833, 0, 1_045_833),
			new MonthlyInvestmentResult(12, 1_000_000, 50_000, 0, 1_050_000)
		);
		CalculateMonthlyInvestmentResponse expected = new CalculateMonthlyInvestmentResponse(
			monthlyInvestmentResults);
		assertEquals(expected, response);
	}

	@DisplayName("월별 투자 금액 계산 - 고정 예금, 복리, 비과세")
	@Test
	void calMonthlyInvestmentAmount_whenCompoundFixedDeposit() {
		CalculateInvestmentRequest request = CalculateInvestmentRequest.builder()
			.type(FIXED_DEPOSIT.getTypeName())
			.amountType(AmountType.ONE_TIME.getDescription())
			.amount(1_000_000)
			.periodType(PeriodType.MONTH.getDisplayName())
			.periodValue(12)
			.interestType(COMPOUND.getTypeName())
			.annualInterestRate(0.05)
			.taxType(TaxType.NON_TAX.getDescription())
			.taxRate(0.0)
			.build();
		CalculateMonthlyInvestmentResponse response = usecase.calMonthlyInvestmentAmount(
			request);

		List<MonthlyInvestmentResult> monthlyInvestmentResults = List.of(
			new MonthlyInvestmentResult(1, 1_000_000, 4_167, 0, 1_004_167),
			new MonthlyInvestmentResult(2, 1_000_000, 8_351, 0, 1_008_351),
			new MonthlyInvestmentResult(3, 1_000_000, 12_552, 0, 1_012_552),
			new MonthlyInvestmentResult(4, 1_000_000, 16_771, 0, 1_016_771),
			new MonthlyInvestmentResult(5, 1_000_000, 21_008, 0, 1_021_008),
			new MonthlyInvestmentResult(6, 1_000_000, 25_262, 0, 1_025_262),
			new MonthlyInvestmentResult(7, 1_000_000, 29_534, 0, 1_029_534),
			new MonthlyInvestmentResult(8, 1_000_000, 33_824, 0, 1_033_824),
			new MonthlyInvestmentResult(9, 1_000_000, 38_131, 0, 1_038_131),
			new MonthlyInvestmentResult(10, 1_000_000, 42_457, 0, 1_042_457),
			new MonthlyInvestmentResult(11, 1_000_000, 46_800, 0, 1_046_800),
			new MonthlyInvestmentResult(12, 1_000_000, 51_162, 0, 1_051_162)
		);
		CalculateMonthlyInvestmentResponse expected = new CalculateMonthlyInvestmentResponse(
			monthlyInvestmentResults);
		assertEquals(expected, response);
	}

}
