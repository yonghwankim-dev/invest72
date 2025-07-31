package application.factory;

import static co.invest72.investment.domain.interest.InterestType.*;
import static co.invest72.investment.domain.investment.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.request.CalculateInvestmentRequest;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.investment.CompoundFixedDeposit;
import co.invest72.investment.domain.investment.CompoundFixedInstallmentSaving;
import co.invest72.investment.domain.investment.ExpirationInvestmentFactory;
import co.invest72.investment.domain.investment.InvestmentFactory;
import co.invest72.investment.domain.investment.SimpleFixedDeposit;
import co.invest72.investment.domain.investment.SimpleFixedInstallmentSaving;
import co.invest72.investment.domain.tax.TaxType;

class ExpirationInvestmentFactoryTest {

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
		investmentFactory = new ExpirationInvestmentFactory();
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
		assertInstanceOfInvestment(SimpleFixedDeposit.class, investment);
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
		assertInstanceOfInvestment(CompoundFixedDeposit.class, investment);
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
