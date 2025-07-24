package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

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

class SimpleFixedInstallmentSavingTest {

	private SimpleFixedInstallmentSaving investment;
	private InstallmentInvestmentAmount investmentAmount;
	private InvestPeriod investPeriod;
	private InterestRate annualInterestRateRate;
	private Taxable taxable;

	@BeforeEach
	void setUp() {
		investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		investPeriod = new MonthlyInvestPeriod(12);
		annualInterestRateRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);
	}

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/simple_fixed_installment_saving_1y_5percent_standard_tax.csv", numLinesToSkip = 1)
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
	void shouldReturnPrincipalAmount_whenInvestPeriodIsExpiration() {
		int principalAmount = investment.getPrincipal();

		int expectedPrincipalAmount = 12_000_000;
		assertEquals(expectedPrincipalAmount, principalAmount);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowException_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getPrincipal(month));
	}

	@Test
	void shouldReturnInterest() {
		int interest = investment.getInterest();

		int expectedInterest = 325_000;
		assertEquals(expectedInterest, interest);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedInterest_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getInterest(month));
	}

	@Test
	void shouldReturnTax_whenTaxTypeIsStandard() {
		assertEquals(50_050, investment.getTax());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTax_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getTax(month));
	}

	@Test
	void shouldReturnTotalProfit() {
		int amount = investment.getTotalProfit();

		int expectedAmount = 12_274_950;
		assertEquals(expectedAmount, amount);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTotalProfit_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getTotalProfit(month));
	}

	@Test
	void shouldReturnFinalMonth() {
		assertEquals(12, investment.getFinalMonth());
	}
}
