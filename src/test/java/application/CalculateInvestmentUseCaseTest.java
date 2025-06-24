package application;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;
import static org.junit.jupiter.api.Assertions.*;

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

	@Test
	void created() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();

		InvestmentUseCase investmentUseCase = new CalculateInvestmentUseCase(investmentFactory);

		assertNotNull(investmentUseCase);
	}

	@Test
	void shouldReturnAmount_whenRequestIsSimpleFixedDeposit() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase investmentUseCase = new CalculateInvestmentUseCase(investmentFactory);

		InvestmentType investmentType = FIXED_DEPOSIT;
		InvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		InterestType interestType = SIMPLE;
		InterestRate interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
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
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase investmentUseCase = new CalculateInvestmentUseCase(investmentFactory);

		InvestmentType investmentType = FIXED_DEPOSIT;
		InvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		InterestType interestType = COMPOUND;
		InterestRate interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
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
