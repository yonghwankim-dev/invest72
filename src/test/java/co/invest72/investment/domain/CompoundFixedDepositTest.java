package co.invest72.investment.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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

	private Investment investment;

	@BeforeEach
	void setUp() {
		LumpSumInvestmentAmount depositAmount = new FixedDepositAmount(1_000_000);
		InterestRate interestRate = new AnnualInterestRate(0.05);
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new CompoundFixedDeposit(
			depositAmount,
			investPeriod, interestRate,
			taxable
		);
	}

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/compound_fixed_deposit_1y_5percent_standard_tax.csv", numLinesToSkip = 1)
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

	@Test
	void shouldReturnInterest() {
		int interest = investment.getInterest();

		assertEquals(51_162, interest);
	}

	@Test
	void shouldReturnTax() {
		int tax = investment.getTax();

		assertEquals(7_879, tax);
	}

	@Test
	void shouldReturnTotalProfit() {
		int totalProfit = investment.getTotalProfit();

		assertEquals(1_043_283, totalProfit);
	}
}
