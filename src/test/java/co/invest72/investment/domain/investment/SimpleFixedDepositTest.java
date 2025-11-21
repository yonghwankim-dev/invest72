package co.invest72.investment.domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.LumpSumInvestmentAmount;
import co.invest72.investment.domain.PeriodRange;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.MonthlyInvestPeriod;
import co.invest72.investment.domain.period.PeriodYearRange;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;

class SimpleFixedDepositTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		InterestRate interestRate = new AnnualInterestRate(0.05);
		PeriodRange periodRange = new PeriodYearRange(1);
		InvestPeriod investPeriod = new MonthlyInvestPeriod(periodRange.toMonths());
		investment = new SimpleFixedDeposit(investmentAmount, investPeriod, interestRate,
			taxable);
	}

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/simple_fixed_deposit_1y_5percent_standard_tax.csv", numLinesToSkip = 1)
	void shouldReturnInvestmentAmount(int month, int expectedPrincipal, int expectedInterest, int expectedTax,
		int expectedTotalProfit) {
		int principal = investment.getPrincipal(month);
		int interest = investment.getInterest(month);
		int tax = investment.getTax(month);
		int totalProfit = investment.getProfit(month);

		assertEquals(expectedPrincipal, principal);
		assertEquals(expectedInterest, interest);
		assertEquals(expectedTax, tax);
		assertEquals(expectedTotalProfit, totalProfit);
	}

	@Test
	void shouldReturnPrincipal() {
		int principal = investment.getPrincipal();

		assertEquals(1_000_000, principal);
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
	void getPrincipal_whenValidMonths_thenReturnPrincipal(int month) {
		int principal = investment.getPrincipal(month);

		assertEquals(1_000_000, principal);
	}

	@Test
	void getPrincipal_whenMonthsIsNegative_thenReturnPrincipal() {
		int month = -1;

		int principal = investment.getPrincipal(month);

		assertEquals(1_000_000, principal);
	}

	@Test
	void getPrincipal_whenMonthsIsZero_thenReturnPrincipal() {
		int month = 0;

		int principal = investment.getPrincipal(month);

		assertEquals(1_000_000, principal);
	}

	@Test
	void getPrincipal_whenMonthsGreaterThanFinalMonth_thenReturnFinalMonthPrincipal() {
		int month = 13;

		int principal = investment.getPrincipal(month);

		assertEquals(1_000_000, principal);
	}

	@Test
	void shouldReturnInterest() {
		int interest = investment.getInterest();

		assertEquals(4_167, interest);
	}

	@Test
	void getInterest_whenMonthsIsZero_thenReturnZeroInterest() {
		int months = 0;

		int interest = investment.getInterest(months);

		assertEquals(0, interest);
	}

	@Test
	void getInterest_whenMonthsIsNegative_thenReturnZeroInterest() {
		int months = -1;

		int interest = investment.getInterest(months);

		assertEquals(0, interest);
	}

	@Test
	void getInterest_whenMonthGreaterThanFinalMonth_thenReturnFinalMonthInterest() {
		int month = 13;

		int interest = investment.getInterest(month);

		assertEquals(4_167, interest);
	}

	@Test
	void shouldReturnTax() {
		int tax = investment.getTax();

		assertEquals(642, tax);
	}

	@Test
	void shouldReturnTotalProfit() {
		int totalProfit = investment.getProfit();

		assertEquals(1_003_525, totalProfit);
	}

	@Test
	void shouldReturnFinalMonth() {
		int finalMonth = investment.getFinalMonth();

		assertEquals(12, finalMonth);
	}

	@Test
	void getProfit_whenMonthsIsNegative_thenReturnProfit() {
		int months = -1;

		int profit = investment.getProfit(months);

		assertEquals(1_000_000, profit);
	}

	@Test
	void getProfit_whenMonthsGreaterThanFinalMonth_thenReturnFinalMonthProfit() {
		int month = 13;

		int profit = investment.getProfit(month);

		assertEquals(1_003_525, profit);
	}
}
