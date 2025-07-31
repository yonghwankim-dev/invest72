package application.usecase;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.factory.ExpirationInvestmentFactory;
import application.factory.InvestmentFactory;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import application.response.CalculateMonthlyInvestmentResponse;
import application.response.MonthlyInvestmentResult;
import co.invest72.investment.domain.Investment;
import domain.type.TaxType;

class CalculateInvestmentUseCaseTest {

	private InvestmentUseCase investmentUseCase;
	private String investmentType;
	private String investmentAmount;
	private String periodType;
	private int periodValue;
	private String interestType;
	private double annualInterestRate;
	private String taxable;
	private double taxRate;
	private CalculateInvestmentRequest request;

	@BeforeEach
	void setUp() {
		InvestmentFactory<Investment> investmentFactory = new ExpirationInvestmentFactory();
		investmentUseCase = new CalculateInvestmentUseCase(investmentFactory);
		investmentType = FIXED_DEPOSIT.getTypeName();
		investmentAmount = "1000000";
		periodType = "년";
		periodValue = 1;
		interestType = SIMPLE.getTypeName();
		annualInterestRate = 0.05;
		taxable = TaxType.NON_TAX.getDescription();
		taxRate = 0.0; // 비과세이므로 세율은 0.0

		request = new CalculateInvestmentRequest(
			investmentType,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			annualInterestRate,
			taxable,
			taxRate
		);
	}

	@Test
	void calAmount_shouldReturnCalAmountResponse() {
		investmentType = INSTALLMENT_SAVING.getTypeName();
		investmentAmount = "월 1000000";
		interestType = COMPOUND.getTypeName();

		request = new CalculateInvestmentRequest(
			investmentType,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			annualInterestRate,
			taxable,
			taxRate
		);

		CalculateInvestmentResponse response = investmentUseCase.calInvestmentAmount(request);

		int expectedTotalProfitAmount = 12_330_017;
		assertEquals(expectedTotalProfitAmount, response.getTotalProfitAmount());

		int expectedTotalPrincipal = 12_000_000;
		assertEquals(expectedTotalPrincipal, response.getTotalPrincipalAmount());

		int expectedInterest = 330_017;
		assertEquals(expectedInterest, response.getInterest());

		int expectedTax = 0;
		assertEquals(expectedTax, response.getTax());
	}

	@Test
	void calMonthlyInvestmentAmount_created() {
		CalculateMonthlyInvestmentResponse response = investmentUseCase.calMonthlyInvestmentAmount(request);

		assertNotNull(response);
	}

	@Test
	void calMonthlyInvestmentAmount_shouldReturnResponse() {
		request = new CalculateInvestmentRequest(
			FIXED_DEPOSIT.getTypeName(),
			"1000000",
			"월",
			4,
			SIMPLE.getTypeName(),
			0.05,
			TaxType.STANDARD.getDescription(),
			0.154
		);
		CalculateMonthlyInvestmentResponse response = investmentUseCase.calMonthlyInvestmentAmount(request);

		List<MonthlyInvestmentResult> monthlyInvestmentResults = List.of(
			new MonthlyInvestmentResult(1, 1_000_000, 4_167, 642, 1_003_525),
			new MonthlyInvestmentResult(2, 1_000_000, 8_333, 1_283, 1_007_050),
			new MonthlyInvestmentResult(3, 1_000_000, 12_500, 1_925, 1_010_575),
			new MonthlyInvestmentResult(4, 1_000_000, 16_667, 2_567, 1_014_100)
		);
		CalculateMonthlyInvestmentResponse expected = new CalculateMonthlyInvestmentResponse(monthlyInvestmentResults);
		assertEquals(expected, response);
	}
}
