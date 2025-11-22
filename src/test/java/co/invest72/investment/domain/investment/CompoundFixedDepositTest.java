package co.invest72.investment.domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.LumpSumInvestmentAmount;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.YearlyInvestPeriod;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;

class CompoundFixedDepositTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		LumpSumInvestmentAmount depositAmount = new FixedDepositAmount(1_000_000);
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		InterestRate interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = CompoundFixedDeposit.builder()
			.investmentAmount(depositAmount)
			.investPeriod(investPeriod)
			.interestRate(interestRate)
			.taxable(taxable)
			.build();
	}

	@ParameterizedTest
	@CsvFileSource(files = "src/test/resources/compound_fixed_deposit_1y_5percent_standard_tax.csv", numLinesToSkip = 1)
	void shouldReturnInvestmentAmount(int month, int expectedPrincipal, int expectedInterest, int expectedTax,
		int expectedTotalProfit) {
		int principal = investment.getPrincipal(month);
		int interest = investment.getInterest(month);
		int totalProfit = investment.getProfit(month);

		assertEquals(expectedPrincipal, principal);
		assertEquals(expectedInterest, interest);
		assertEquals(expectedTotalProfit, totalProfit);
	}

	@Test
	void getPrincipal() {
		int principal = investment.getPrincipal();

		assertEquals(1_046_800, principal);
	}

	@Test
	void getPrincipal_whenMonthIsNegative() {
		int principal = investment.getPrincipal(-1);

		assertEquals(1_000_000, principal);
	}

	@Test
	void getPrincipal_whenMonthIsZero() {
		int principal = investment.getPrincipal(0);

		assertEquals(1_000_000, principal);
	}

	@Test
	void getPrincipal_whenMonthGreaterThanFinalMonth() {
		int principal = investment.getPrincipal(13);

		assertEquals(1_046_800, principal);
	}

	@Test
	void getInterest() {
		int interest = investment.getInterest();

		assertEquals(4_362, interest);
	}

	@Test
	void getInterest_whenMonthIsNegative() {
		int interest = investment.getInterest(-1);

		assertEquals(0, interest);
	}

	@Test
	void getInterest_whenMonthIsZero() {
		int interest = investment.getInterest(0);

		assertEquals(0, interest);
	}

	@Test
	void getInterest_whenMonthGreaterThanFinalMonth() {
		int interest = investment.getInterest(13);

		assertEquals(4_362, interest);
	}

	@Test
	void getTax() {
		int tax = investment.getTax();

		assertEquals(672, tax);
	}

	@Test
	void getTax_whenMonthIsNegative() {
		int tax = investment.getTax(-1);

		assertEquals(0, tax);
	}

	@Test
	void getTax_whenMonthIsZero() {
		int tax = investment.getTax(0);

		assertEquals(0, tax);
	}

	@Test
	void getTax_whenMonthGreaterThanFinalMonth() {
		int tax = investment.getTax(13);

		assertEquals(672, tax);
	}

	@Test
	void getProfit() {
		int totalProfit = investment.getProfit();

		assertEquals(1_051_162, totalProfit);
	}

	@Test
	void getTotalPrincipal() {
		int totalPrincipal = investment.getTotalPrincipal();

		assertEquals(1_046_800, totalPrincipal);
	}

	@Test
	void getTotalInterest() {
		int totalInterest = investment.getTotalInterest();

		assertEquals(51_162, totalInterest);
	}
}
