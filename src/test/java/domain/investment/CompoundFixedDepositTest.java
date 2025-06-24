package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class CompoundFixedDepositTest {

	private LumpSumInvestmentAmount depositAmount;
	private InterestRate interestRate;
	private InvestPeriod investPeriod;
	private TaxableFactory taxableFactory;
	private Taxable taxable;
	private Investment investment;

	@BeforeEach
	void setUp() {
		depositAmount = new FixedDepositAmount(1_000_000);
		interestRate = new AnnualInterestRate(0.05);
		investPeriod = new YearlyInvestPeriod(1);
		taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
		investment = new CompoundFixedDeposit(
			depositAmount,
			investPeriod, interestRate,
			taxable
		);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsCompound() {
		int amount = investment.getAmount();

		int expectedAmount = 1_051_162;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsZero() {
		interestRate = new AnnualInterestRate(0);
		investment = new CompoundFixedDeposit(
			depositAmount,
			investPeriod, interestRate,
			taxable
		);

		int amount = investment.getAmount();

		int expectedAmount = 1_000_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenYearsIsZero() {
		investPeriod = new YearlyInvestPeriod(0);
		investment = new CompoundFixedDeposit(
			depositAmount,
			investPeriod, interestRate,
			taxable
		);

		int amount = investment.getAmount();

		int expectedAmount = 1_000_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnZeroAmount_whenAmountIsZero() {
		depositAmount = new FixedDepositAmount(0);
		investment = new CompoundFixedDeposit(
			depositAmount,
			investPeriod, interestRate,
			taxable
		);

		int amount = investment.getAmount();

		int expectedAmount = 0;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsCompoundAndStandardTax() {
		taxable = taxableFactory.createStandardTax(0.154);
		investment = new CompoundFixedDeposit(
			depositAmount,
			investPeriod, interestRate,
			taxable
		);

		int amount = investment.getAmount();

		int expectedAmount = 1_043_283;
		assertEquals(expectedAmount, amount);
	}
}
