package application;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InterestType;

class CalculateInvestmentUseCaseTest {

	private InvestmentFactory investmentFactory;
	private InvestmentUseCase investmentUseCase;
	private String investmentType;
	private String investmentAmount;
	private String periodType;
	private int periodValue;
	private InterestType interestType;
	private InterestRate interestRate;
	private Taxable taxable;

	@BeforeEach
	void setUp() {
		investmentFactory = new DefaultInvestmentFactory();
		investmentUseCase = new CalculateInvestmentUseCase(investmentFactory);
		investmentType = FIXED_DEPOSIT.getTypeName();
		investmentAmount = "1000000";
		periodType = "년";
		periodValue = 1;
		interestType = SIMPLE;
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
	}

	@Test
	void created() {
		investmentFactory = new DefaultInvestmentFactory();

		investmentUseCase = new CalculateInvestmentUseCase(investmentFactory);

		assertNotNull(investmentUseCase);
	}

	@Test
	void shouldReturnAmount_whenRequestIsSimpleFixedDeposit() {
		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			investmentType,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			interestRate,
			taxable
		);

		int amount = investmentUseCase.calAmount(request);

		int expectedAmount = 1_050_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenRequestIsCompoundFixedDeposit() {
		interestType = COMPOUND;

		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			investmentType,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			interestRate,
			taxable
		);

		int amount = investmentUseCase.calAmount(request);

		int expectedAmount = 1_051_162;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenRequestIsSimpleInstallmentSaving() {
		investmentType = INSTALLMENT_SAVING.getTypeName();
		investmentAmount = "월 1000000";
		interestType = SIMPLE;

		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			investmentType,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			interestRate,
			taxable
		);

		int amount = investmentUseCase.calAmount(request);

		int expectedAmount = 12_325_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenRequestIsCompoundInstallmentSaving() {
		investmentType = INSTALLMENT_SAVING.getTypeName();
		investmentAmount = "월 1000000";
		interestType = COMPOUND;

		CalculateInvestmentRequest request = new CalculateInvestmentRequest(
			investmentType,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			interestRate,
			taxable
		);

		int amount = investmentUseCase.calAmount(request);

		int expectedAmount = 12_330_017;
		assertEquals(expectedAmount, amount);
	}
}
