package co.invest72.investment.domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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

class SimpleFixedInstallmentSavingTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		InterestRate annualInterestRateRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);
	}

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/simple_fixed_installment_saving_1y_5percent_standard_tax.csv", numLinesToSkip = 1)
	void shouldReturnInvestmentAmount(int month, int expectedPrincipal, int expectedInterest, int expectedTotalProfit) {
		int principal = investment.getPrincipal(month);
		int interest = investment.getInterest(month);
		int totalProfit = investment.getProfit(month);

		assertEquals(expectedPrincipal, principal);
		assertEquals(expectedInterest, interest);
		assertEquals(expectedTotalProfit, totalProfit);
	}

	@Test
	void shouldReturnPrincipalAmount_whenInvestPeriodIsExpiration() {
		int principalAmount = investment.getPrincipal();

		int expectedPrincipalAmount = 12_000_000;
		assertEquals(expectedPrincipalAmount, principalAmount);
	}

	@Test
	void getPrincipal_whenMonthIsZero_thenReturnZero() {
		int months = 0;

		int principal = investment.getPrincipal(months);

		assertEquals(0, principal);
	}

	@Test
	void getInterest() {
		int interest = investment.getInterest();

		assertEquals(50_000, interest);
	}

	@Test
	void getInterest_whenMonthsIsZero_thenReturnZeroInterest() {
		int months = 0;

		int interest = investment.getInterest(months);

		assertEquals(0, interest);
	}

	@Test
	void shouldReturnTotalProfit() {
		int amount = investment.getProfit();

		assertEquals(12_050_000, amount);
	}

	@Test
	void getTotalProfit_whenMonthsIsZero_thenReturnZeroTotalProfit() {
		int months = 0;

		int totalProfit = investment.getProfit(months);

		assertEquals(0, totalProfit);
	}

	@Test
	void getTotalInterest() {
		int totalInterest = investment.getTotalInterest();

		int expectedTotalInterest = 325_000;
		assertEquals(expectedTotalInterest, totalInterest);
	}

	@Test
	void getFinalMonth() {
		assertEquals(12, investment.getFinalMonth());
	}
}
