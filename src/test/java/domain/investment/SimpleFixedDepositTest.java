package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.amount.FixedDepositAmount;
import domain.amount.LumpSumInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.MonthlyInvestPeriod;
import domain.invest_period.PeriodRange;
import domain.invest_period.PeriodYearRange;
import domain.invest_period.RemainingPeriodProvider;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class SimpleFixedDepositTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		InterestRate interestRate = new AnnualInterestRate(0.05);
		PeriodRange periodRange = new PeriodYearRange(1);
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(periodRange);
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
		int totalProfit = investment.getTotalProfit(month);

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
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getPrincipal(month));
	}

	@Test
	void shouldReturnInterest() {
		int interest = investment.getInterest();

		assertEquals(50_000, interest);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetInterest_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getInterest(month));
	}

	@Test
	void shouldReturnTax() {
		int tax = investment.getTax();

		assertEquals(7_700, tax);
	}

	@Test
	void shouldReturnTotalProfit() {
		int totalProfit = investment.getTotalProfit();

		assertEquals(1_042_300, totalProfit);
	}

	@Test
	void shouldReturnFinalMonth() {
		int finalMonth = investment.getFinalMonth();

		assertEquals(12, finalMonth);
	}
}
