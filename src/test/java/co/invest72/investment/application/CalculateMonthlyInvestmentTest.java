package co.invest72.investment.application;

import static co.invest72.investment.domain.interest.InterestType.*;
import static co.invest72.investment.domain.investment.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.application.dto.CalculateInvestmentRequest;
import co.invest72.investment.domain.tax.TaxType;

class CalculateMonthlyInvestmentTest {

	private CalculateMonthlyInvestment usecase;

	@BeforeEach
	void setUp() {
		InvestmentFactory investmentFactory = new InvestmentFactory();
		usecase = new CalculateMonthlyInvestment(investmentFactory);
	}

	@Test
	void calMonthlyInvestmentAmount_shouldReturnResponse() {
		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			FIXED_DEPOSIT.getTypeName(),
			"1000000",
			"월",
			4,
			SIMPLE.getTypeName(),
			0.05,
			TaxType.STANDARD.getDescription(),
			0.154
		);
		CalculateMonthlyInvestment.CalculateMonthlyInvestmentResponse response = usecase.calMonthlyInvestmentAmount(
			request);

		List<CalculateMonthlyInvestment.MonthlyInvestmentResult> monthlyInvestmentResults = List.of(
			new CalculateMonthlyInvestment.MonthlyInvestmentResult(1, 1_000_000, 4_167, 642, 1_003_525),
			new CalculateMonthlyInvestment.MonthlyInvestmentResult(2, 1_000_000, 8_333, 1_283, 1_007_050),
			new CalculateMonthlyInvestment.MonthlyInvestmentResult(3, 1_000_000, 12_500, 1_925, 1_010_575),
			new CalculateMonthlyInvestment.MonthlyInvestmentResult(4, 1_000_000, 16_667, 2_567, 1_014_100)
		);
		CalculateMonthlyInvestment.CalculateMonthlyInvestmentResponse expected = new CalculateMonthlyInvestment.CalculateMonthlyInvestmentResponse(
			monthlyInvestmentResults);
		assertEquals(expected, response);
	}

}
