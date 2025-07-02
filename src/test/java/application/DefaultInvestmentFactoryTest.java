package application;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class DefaultInvestmentFactoryTest {

	private InvestmentFactory investmentFactory;
	private CalculateInvestmentRequest request;
	private Investment investment;
	private String type;
	private String investmentAmount;
	private String periodType;
	private int periodValue;
	private InterestRate interestRate;

	private void assertInstanceOfInvestment(Class<?> expectedType, Investment investment) {
		assertInstanceOf(expectedType, investment);
	}

	@BeforeEach
	void setUp() {
		investmentFactory = new DefaultInvestmentFactory();
	}

	@Test
	void shouldReturnInvestment_whenRequestIsSimpleFixedDeposit() {
		type = FIXED_DEPOSIT.getTypeName();
		investmentAmount = "1000000";
		periodType = "년";
		periodValue = 1;
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		request = new CalculateInvestmentRequest(
			type,
			investmentAmount,
			periodType,
			periodValue,
			SIMPLE,
			interestRate,
			taxable
		);
		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(domain.investment.SimpleFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfCompoundFixedDeposit_whenRequestIsCompoundFixedDeposit() {
		type = FIXED_DEPOSIT.getTypeName();
		investmentAmount = "1000000";
		periodType = "년";
		periodValue = 1;
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		request = new CalculateInvestmentRequest(
			type,
			investmentAmount,
			periodType,
			periodValue,
			COMPOUND,
			interestRate,
			taxable
		);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(domain.investment.CompoundFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfSimpleFixedInstallmentSaving_whenRequestIsSimpleFixedInstallmentSaving() {
		type = INSTALLMENT_SAVING.getTypeName();
		investmentAmount = "월 1000000";
		periodType = "년";
		periodValue = 1;
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		request = new CalculateInvestmentRequest(
			type,
			investmentAmount,
			periodType,
			periodValue,
			SIMPLE,
			interestRate,
			taxable
		);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(SimpleFixedInstallmentSaving.class, investment);
	}

	@Test
	void shouldInstanceOfCompoundFixedInstallmentSaving_whenRequestIsCompoundFixedInstallmentSaving() {
		type = INSTALLMENT_SAVING.getTypeName();
		investmentAmount = "월 1000000";
		periodType = "년";
		periodValue = 1;
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		request = new CalculateInvestmentRequest(
			type,
			investmentAmount,
			periodType,
			periodValue,
			COMPOUND,
			interestRate,
			taxable
		);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(CompoundFixedInstallmentSaving.class, investment);
	}
}
