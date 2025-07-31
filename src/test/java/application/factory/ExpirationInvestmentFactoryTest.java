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
import co.invest72.investment.domain.investment.SimpleFixedDeposit;
import co.invest72.investment.domain.investment.SimpleFixedInstallmentSaving;
import co.invest72.investment.domain.tax.TaxType;

class ExpirationInvestmentFactoryTest {

	private ExpirationInvestmentFactory investmentFactory;
	private CalculateInvestmentRequest request;
	private Investment investment;

	private void assertInstanceOfInvestment(Class<?> expectedType, Investment investment) {
		assertInstanceOf(expectedType, investment);
	}

	@BeforeEach
	void setUp() {
		investmentFactory = new ExpirationInvestmentFactory();
	}

	@Test
	void shouldReturnInvestment_whenRequestIsSimpleFixedDeposit() {
		request = CalculateInvestmentRequest.builder()
			.type(FIXED_DEPOSIT.getTypeName())
			.amount("1000000")
			.periodType("년")
			.periodValue(1)
			.interestType(SIMPLE.getTypeName())
			.interestRate(0.05)
			.taxType(TaxType.NON_TAX.getDescription())
			.taxRate(0.0)
			.build();

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(SimpleFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfCompoundFixedDeposit_whenRequestIsCompoundFixedDeposit() {
		request = CalculateInvestmentRequest.builder()
			.type(FIXED_DEPOSIT.getTypeName())
			.amount("1000000")
			.periodType("년")
			.periodValue(1)
			.interestType(COMPOUND.getTypeName())
			.interestRate(0.05)
			.taxType(TaxType.NON_TAX.getDescription())
			.taxRate(0.0)
			.build();

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(CompoundFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfSimpleFixedInstallmentSaving_whenRequestIsSimpleFixedInstallmentSaving() {
		request = CalculateInvestmentRequest.builder()
			.type(INSTALLMENT_SAVING.getTypeName())
			.amount("월 1000000")
			.periodType("년")
			.periodValue(1)
			.interestType(SIMPLE.getTypeName())
			.interestRate(0.05)
			.taxType(TaxType.NON_TAX.getDescription())
			.taxRate(0.0)
			.build();

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(SimpleFixedInstallmentSaving.class, investment);
	}

	@Test
	void shouldInstanceOfCompoundFixedInstallmentSaving_whenRequestIsCompoundFixedInstallmentSaving() {
		request = CalculateInvestmentRequest.builder()
			.type(INSTALLMENT_SAVING.getTypeName())
			.amount("월 1000000")
			.periodType("년")
			.periodValue(1)
			.interestType(COMPOUND.getTypeName())
			.interestRate(0.05)
			.taxType(TaxType.NON_TAX.getDescription())
			.taxRate(0.0)
			.build();

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(CompoundFixedInstallmentSaving.class, investment);
	}
}
