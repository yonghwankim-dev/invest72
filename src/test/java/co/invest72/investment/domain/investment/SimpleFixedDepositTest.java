package co.invest72.investment.domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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
		PeriodRange periodRange = new PeriodYearRange(1);
		InvestPeriod investPeriod = new MonthlyInvestPeriod(periodRange.toMonths());
		InterestRate interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = SimpleFixedDeposit.builder()
			.investmentAmount(investmentAmount)
			.investPeriod(investPeriod)
			.interestRate(interestRate)
			.taxable(taxable)
			.build();
	}

	@DisplayName("월별 투자 금액 계산 - 고정 예금, 단리, 일반 과세")
	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/simple_fixed_deposit_1y_5percent_standard_tax.csv", numLinesToSkip = 1)
	void shouldReturnInvestmentAmount(int month, int expectedPrincipal, int expectedInterest, int expectedTax,
		int expectedProfit) {
		int principal = investment.getPrincipal(month);
		int interest = investment.getInterest(month);
		int tax = investment.getTax(month);
		int profit = investment.getProfit(month);

		assertEquals(expectedPrincipal, principal);
		assertEquals(expectedInterest, interest);
		assertEquals(expectedTax, tax);
		assertEquals(expectedProfit, profit);
	}

	@Test
	void getPrincipal() {
		int principal = investment.getPrincipal();

		assertEquals(1_000_000, principal);
	}

	@Test
	void getPrincipal_forAllMonths() {
		IntStream.rangeClosed(1, 12).forEach(month -> {
			int principal = investment.getPrincipal(month);
			assertEquals(1_000_000, principal);
		});
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
	void getInterest() {
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
	void getTax() {
		int tax = investment.getTax();

		assertEquals(642, tax);
	}

	@Test
	void getTax_whenMonthsIsNegative_thenReturnZeroTax() {
		int months = -1;

		int tax = investment.getTax(months);

		assertEquals(0, tax);
	}

	@Test
	void getTax_whenMonthsIsZero_thenReturnTax() {
		int months = 0;

		int tax = investment.getTax(months);

		assertEquals(0, tax);
	}

	@Test
	void getTax_whenMonthsGreaterThanFinalMonth_thenReturnFinalMonthTax() {
		int month = 13;

		int tax = investment.getTax(month);

		assertEquals(642, tax);
	}

	@Test
	void getProfit() {
		int profit = investment.getProfit();

		assertEquals(1_003_525, profit);
	}

	@Test
	void getFinalMonth() {
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
	void getProfit_whenMonthsIsZero_thenReturnProfit() {
		int months = 0;

		int profit = investment.getProfit(months);

		assertEquals(1_000_000, profit);
	}

	@Test
	void getProfit_whenMonthsGreaterThanFinalMonth_thenReturnFinalMonthProfit() {
		int month = 13;

		int profit = investment.getProfit(month);

		assertEquals(1_003_525, profit);
	}

	@Test
	void getTotalPrincipal() {
		int totalPrincipal = investment.getTotalPrincipal();

		assertEquals(1_000_000, totalPrincipal);
	}

	@Test
	void getTotalInterest() {
		int totalInterest = investment.getTotalInterest();

		assertEquals(50_000, totalInterest);
	}

	@Test
	void getTotalTax() {
		int totalTax = investment.getTotalTax();

		assertEquals(7_700, totalTax);
	}

	@Test
	void getTotalProfit() {
		int totalProfit = investment.getTotalProfit();

		assertEquals(1_042_300, totalProfit);
	}
}
