package application.factory;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.request.CalculateInvestmentRequest;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.type.TaxType;

class DefaultInvestmentFactoryTest {

	private InvestmentFactory<Investment> investmentFactory;
	private CalculateInvestmentRequest request;
	private Investment investment;
	private String type;
	private String investmentAmount;
	private String periodType;
	private int periodValue;
	private String interestType;
	private double annualInterestRate;
	private String taxable;
	private double taxRate;

	private void assertInstanceOfInvestment(Class<?> expectedType, Investment investment) {
		assertInstanceOf(expectedType, investment);
	}

	@BeforeEach
	void setUp() {
		investmentFactory = new DefaultInvestmentFactory();
		taxable = "비과세";
	}

	@Test
	void shouldReturnInvestment_whenRequestIsSimpleFixedDeposit() {
		type = FIXED_DEPOSIT.getTypeName();
		investmentAmount = "1000000";
		periodType = "년";
		periodValue = 1;
		interestType = SIMPLE.getTypeName();
		annualInterestRate = 0.05;
		taxable = TaxType.NON_TAX.getDescription();
		taxRate = 0.0;
		request = new CalculateInvestmentRequest(
			type,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			annualInterestRate,
			taxable,
			taxRate
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
		interestType = COMPOUND.getTypeName();
		annualInterestRate = 0.05;
		request = new CalculateInvestmentRequest(
			type,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			annualInterestRate,
			taxable,
			taxRate
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
		interestType = SIMPLE.getTypeName();
		annualInterestRate = 0.05;
		request = new CalculateInvestmentRequest(
			type,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			annualInterestRate,
			taxable,
			taxRate
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
		interestType = COMPOUND.getTypeName();
		annualInterestRate = 0.05;
		request = new CalculateInvestmentRequest(
			type,
			investmentAmount,
			periodType,
			periodValue,
			interestType,
			annualInterestRate,
			taxable,
			taxRate
		);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(CompoundFixedInstallmentSaving.class, investment);
	}
}
