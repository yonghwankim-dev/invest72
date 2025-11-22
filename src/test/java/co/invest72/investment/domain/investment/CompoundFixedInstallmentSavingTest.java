package co.invest72.investment.domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import co.invest72.investment.domain.InstallmentInvestmentAmount;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.amount.MonthlyInstallmentInvestmentAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.MonthlyInvestPeriod;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;

class CompoundFixedInstallmentSavingTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		InterestRate annualInterestRateRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new CompoundFixedInstallmentSaving(investmentAmount, investPeriod, annualInterestRateRate,
			taxable);

	}

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/compound_fixed_installment_saving_1y_5percent_standard_tax.csv", numLinesToSkip = 1)
	void shouldReturnInvestmentAmount(int month, int expectedPrincipal, int expectedInterest, int expectedTotalProfit) {
		int principal = investment.getPrincipal(month);
		int interest = investment.getInterest(month);
		int totalProfit = investment.getProfit(month);

		assertEquals(expectedPrincipal, principal);
		assertEquals(expectedInterest, interest);
		assertEquals(expectedTotalProfit, totalProfit);
	}

	@Test
	void shouldReturnPrincipal() {
		assertEquals(12_000_000, investment.getPrincipal());
	}

	@Test
	void getPrincipal_whenMonthIsZero_thenReturnPrincipal() {
		int months = 0;

		int principal = investment.getPrincipal(months);

		assertEquals(0, principal);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 13})
	void shouldThrowExceptionForAccumulatedPrincipal_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getPrincipal(month));
	}

	@Test
	void shouldReturnInterest() {
		assertEquals(330_017, investment.getInterest());
	}

	@Test
	void getInterest_whenMonthIsZero_thenReturnZeroInterest() {
		int months = 0;

		int interest = investment.getInterest(months);

		assertEquals(0, interest);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 13})
	void shouldThrowExceptionForGetAccumulatedInterest_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getInterest(month));
	}

	@Test
	void shouldReturnTotalProfit() {
		assertEquals(12_330_017, investment.getProfit());
	}

	@Test
	void getTotalProfit_whenMonthIsZero_thenReturnZeroTotalProfit() {
		int months = 0;

		int totalProfit = investment.getProfit(months);

		assertEquals(0, totalProfit);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 13})
	void shouldThrowExceptionForGetAccumulatedTotalProfit_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getProfit(month));
	}

	@Test
	void shouldReturnFinalMonth() {
		assertEquals(12, investment.getFinalMonth());
	}
}
