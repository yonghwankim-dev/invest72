package application.usecase;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.factory.DefaultInvestmentFactory;
import application.factory.InvestmentFactory;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import domain.type.TaxType;

class CalculateInvestmentUseCaseTest {

	private InvestmentFactory investmentFactory;
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
		investmentFactory = new DefaultInvestmentFactory();
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
}
