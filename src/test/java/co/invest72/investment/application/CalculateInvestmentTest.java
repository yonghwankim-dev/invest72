package co.invest72.investment.application;

import static co.invest72.investment.domain.interest.InterestType.*;
import static co.invest72.investment.domain.investment.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.factory.ExpirationInvestmentFactory;
import application.factory.InvestmentFactory;
import application.request.CalculateInvestmentRequest;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.tax.TaxType;

class CalculateInvestmentTest {

	private CalculateExpirationInvestment investmentUseCase;
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
		investmentUseCase = new CalculateExpirationInvestment(investmentFactory);
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

		CalculateExpirationInvestment.CalculateExpirationInvestmentResponse response = investmentUseCase.calInvestment(
			request);

		int expectedTotalProfitAmount = 12_330_017;
		assertEquals(expectedTotalProfitAmount, response.totalProfitAmount());

		int expectedTotalPrincipal = 12_000_000;
		assertEquals(expectedTotalPrincipal, response.totalPrincipalAmount());

		int expectedInterest = 330_017;
		assertEquals(expectedInterest, response.interest());

		int expectedTax = 0;
		assertEquals(expectedTax, response.tax());
	}
}
