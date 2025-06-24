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
import domain.invest_period.InvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InterestType;
import domain.type.InvestmentType;

class CalculateInvestmentUseCaseTest {

	private InvestmentFactory investmentFactory;
	private InvestmentUseCase investmentUseCase;
	private InvestmentType investmentType;
	private InvestmentAmount investmentAmount;
	private InvestPeriod investPeriod;
	private InterestType interestType;
	private InterestRate interestRate;
	private Taxable taxable;

	@BeforeEach
	void setUp() {
		investmentFactory = new DefaultInvestmentFactory();
		investmentUseCase = new CalculateInvestmentUseCase(investmentFactory);
		investmentType = FIXED_DEPOSIT;
		investmentAmount = new FixedDepositAmount(1_000_000);
		investPeriod = new YearlyInvestPeriod(1);
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
		InvestmentRequest request = new InvestmentRequest(
			investmentType,
			investmentAmount,
			investPeriod,
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

		InvestmentRequest request = new InvestmentRequest(
			investmentType,
			investmentAmount,
			investPeriod,
			interestType,
			interestRate,
			taxable
		);

		int amount = investmentUseCase.calAmount(request);

		int expectedAmount = 1_051_162;
		assertEquals(expectedAmount, amount);
	}
}
