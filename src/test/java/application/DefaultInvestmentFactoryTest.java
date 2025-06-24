package application;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InvestmentType;

class DefaultInvestmentFactoryTest {

	private InvestmentRequestFactory investmentFactory;
	private InvestmentRequest request;
	private Investment investment;
	private InvestmentType type;
	private InvestmentAmount investmentAmount;
	private InvestPeriod investPeriod;
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
		type = FIXED_DEPOSIT;
		investmentAmount = new FixedDepositAmount(1_000_000);
		investPeriod = new YearlyInvestPeriod(1);
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		request = new InvestmentRequest(
			type,
			investmentAmount,
			investPeriod,
			SIMPLE,
			interestRate,
			taxable,
			0.0);
		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(domain.investment.SimpleFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfCompoundFixedDeposit_whenRequestIsCompoundFixedDeposit() {
		type = FIXED_DEPOSIT;
		investmentAmount = new FixedDepositAmount(1_000_000);
		investPeriod = new YearlyInvestPeriod(1);
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		request = new InvestmentRequest(
			type,
			investmentAmount,
			investPeriod,
			COMPOUND,
			interestRate,
			taxable,
			0.0);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(domain.investment.CompoundFixedDeposit.class, investment);
	}

	@Test
	void shouldInstanceOfSimpleFixedInstallmentSaving_whenRequestIsSimpleFixedInstallmentSaving() {
		type = INSTALLMENT_SAVINGS;
		investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		investPeriod = new YearlyInvestPeriod(1);
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		request = new InvestmentRequest(
			type,
			investmentAmount,
			investPeriod,
			SIMPLE,
			interestRate,
			taxable,
			0.0);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(SimpleFixedInstallmentSaving.class, investment);
	}

	@Test
	void shouldInstanceOfCompoundFixedInstallmentSaving_whenRequestIsCompoundFixedInstallmentSaving() {
		type = INSTALLMENT_SAVINGS;
		investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		investPeriod = new YearlyInvestPeriod(1);
		interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		request = new InvestmentRequest(
			type,
			investmentAmount,
			investPeriod,
			COMPOUND,
			interestRate,
			taxable,
			0.0);

		investment = investmentFactory.createBy(request);

		assertNotNull(investment);
		assertInstanceOfInvestment(CompoundFixedInstallmentSaving.class, investment);
	}
}
