package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domain.amount.InstallmentInvestmentAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.amount.YearlyInstallmentInvestmentAmount;
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
	private TaxableFactory taxableFactory;

	@BeforeEach
	void setUp() {
		investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		investPeriod = new MonthlyInvestPeriod(12);
		annualInterestRateRate = new AnnualInterestRate(0.05);
		taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);
	}

	@Test
	void created() {
		assertNotNull(investment);
	}

	@Test
	void shouldReturnAmount() {
		int amount = investment.getAmount();

		int expectedAmount = 12_325_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenInvestAmountInstanceOfMonthlyInstallmentInvestmentAmount() {
		investmentAmount = new YearlyInstallmentInvestmentAmount(12_000_000);
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);

		int amount = investment.getAmount();

		int expectedAmount = 12_325_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnPrincipalAmount() {
		int principalAmount = investment.getPrincipalAmount();

		int expectedPrincipalAmount = 12_000_000;
		assertEquals(expectedPrincipalAmount, principalAmount);
	}

	@Test
	void shouldReturnInterest() {
		int interest = investment.getInterest();

		int expectedInterest = 325_000;
		assertEquals(expectedInterest, interest);
	}

	@Test
	void shouldReturnTax_whenTaxTypeIsNonTax() {
		int tax = investment.getTax();

		int expectedTax = 0;
		assertEquals(expectedTax, tax);
	}

	@Test
	void shouldReturnTax_whenTaxTypeIsStandardTax() {
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);

		int tax = investment.getTax();

		int expectedTax = 50_050; // 325,000 * 0.154
		assertEquals(expectedTax, tax);
	}

	@Test
	void shouldReturnAccumulatedPrincipal() {
		int accumulatedPrincipal = investment.getAccumulatedPrincipal(12);

		int expectedAccumulatedPrincipal = 12_000_000;
		assertEquals(expectedAccumulatedPrincipal, accumulatedPrincipal);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowException_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getAccumulatedPrincipal(month));
	}

	@Test
	void shouldReturnAccumulatedInterest() {
		int month = 12;

		int accumulatedInterest = investment.getAccumulatedInterest(month);

		int expectedAccumulatedInterest = 325_000;
		assertEquals(expectedAccumulatedInterest, accumulatedInterest);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedInterest_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getAccumulatedInterest(month));
	}

	@Test
	void shouldReturnAccumulatedTax() {
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);
		int month = 12;

		int accumulatedTax = investment.getAccumulatedTax(month);

		int expectedAccumulatedTax = 50_050; // 325,000 * 0.154
		assertEquals(expectedAccumulatedTax, accumulatedTax);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTax_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getAccumulatedTax(month));
	}

	@Test
	void shouldReturnAccumulatedTotalProfit() {
		int month = 12;

		int accumulatedTotalProfit = investment.getAccumulatedTotalProfit(month);

		int expectedAccumulatedTotalProfit = 12_325_000; // 12,000,000 + 325,000
		assertEquals(expectedAccumulatedTotalProfit, accumulatedTotalProfit);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTotalProfit_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getAccumulatedTotalProfit(month));
	}

	@Test
	void shouldReturnFinalMonth() {
		int finalMonth = investment.getFinalMonth();

		int expectedFinalMonth = 12;
		assertEquals(expectedFinalMonth, finalMonth);
	}
}
