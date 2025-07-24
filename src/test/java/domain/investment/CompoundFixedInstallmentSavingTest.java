package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.amount.InstallmentInvestmentAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

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
	void shouldReturnTotalProfit() {
		assertEquals(12_279_194, investment.getTotalProfit());
	}

	@Test
	void shouldReturnPrincipal() {
		assertEquals(12_000_000, investment.getPrincipal());
	}

	@Test
	void shouldReturnInterest() {
		assertEquals(330_017, investment.getInterest());
	}

	@Test
	void shouldReturnTax() {
		assertEquals(50_823, investment.getTax());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForAccumulatedPrincipal_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getPrincipal(month));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedInterest_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getInterest(month));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTax_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getTax(month));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTotalProfit_whenInvalidMonth(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> investment.getTotalProfit(month));
	}

	@Test
	void shouldReturnFinalMonth() {
		assertEquals(12, investment.getFinalMonth());
	}
}
