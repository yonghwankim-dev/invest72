package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.amount.FixedDepositAmount;
import domain.amount.LumpSumInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.tax.FixedTaxRate;
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

	public static Stream<Arguments> monthSource() {
		return Stream.of(
			Arguments.of(1, 4_166),
			Arguments.of(2, 8350)
		);
	}

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
	void shouldReturnPrincipal() {
		int principal = investment.getPrincipal();

		int expectedPrincipal = 1_000_000;
		assertEquals(expectedPrincipal, principal);
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 12})
	void shouldReturnPrincipal_givenMonth(int month) {
		int principal = investment.getPrincipal(month);

		int expectedPrincipal = 1_000_000;
		assertEquals(expectedPrincipal, principal);
	}

	@Test
	void shouldReturnInterest_whenExpiration() {
		int interest = investment.getInterest();

		int expectedInterest = 51_162;
		assertEquals(expectedInterest, interest);
	}

	@ParameterizedTest
	@MethodSource(value = "monthSource")
	void shouldReturnInterest_whenMonth(int month, int expectedInterest) {
		int interest = investment.getInterest(month);

		assertEquals(expectedInterest, interest);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsCompound() {
		int amount = investment.getTotalProfit();

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

		int amount = investment.getTotalProfit();

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

		int amount = investment.getTotalProfit();

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

		int amount = investment.getTotalProfit();

		int expectedAmount = 0;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsCompoundAndStandardTax() {
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new CompoundFixedDeposit(
			depositAmount,
			investPeriod, interestRate,
			taxable
		);

		int amount = investment.getTotalProfit();

		int expectedAmount = 1_043_284;
		assertEquals(expectedAmount, amount);
	}
}
