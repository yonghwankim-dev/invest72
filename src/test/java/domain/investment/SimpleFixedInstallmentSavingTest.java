package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

	public static Stream<Arguments> fixedInstallmentInvestmentSavingMonthSource() {
		List<Arguments> arguments = new ArrayList<>();
		for (int month = 1; month <= 12; month++) {
			int expectedPrincipal = month * 1_000_000;
			arguments.add(Arguments.of(month, expectedPrincipal));
		}
		return arguments.stream();
	}

	@BeforeEach
	void setUp() {
		investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		investPeriod = new MonthlyInvestPeriod(12);
		annualInterestRateRate = new AnnualInterestRate(0.05);
		taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);
	}

	@Test
	void shouldReturnPrincipalAmount() {
		int principalAmount = investment.getPrincipal();

		int expectedPrincipalAmount = 12_000_000;
		assertEquals(expectedPrincipalAmount, principalAmount);
	}

	@ParameterizedTest
	@MethodSource(value = "fixedInstallmentInvestmentSavingMonthSource")
	void shouldReturnAccumulatedPrincipal(int month, int expectedPrincipal) {
		int principal = investment.getPrincipal(month);

		assertEquals(expectedPrincipal, principal);
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

	@Test
	void shouldReturnAccumulatedInterest() {
		int month = 12;

		int accumulatedInterest = investment.getInterest(month);

		int expectedAccumulatedInterest = 325_000;
		assertEquals(expectedAccumulatedInterest, accumulatedInterest);
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
	void shouldReturnAccumulatedTax() {
		taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);
		int month = 12;

		int accumulatedTax = investment.getTax(month);

		int expectedAccumulatedTax = 50_050; // 325,000 * 0.154
		assertEquals(expectedAccumulatedTax, accumulatedTax);
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

	@Test
	void shouldReturnTotalProfit_whenInvestAmountInstanceOfMonthlyInstallmentInvestmentAmount() {
		investmentAmount = new YearlyInstallmentInvestmentAmount(12_000_000);
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);

		int amount = investment.getTotalProfit();

		int expectedAmount = 12_274_950;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnTotalProfit_givenMonth() {
		int month = 12;

		int accumulatedTotalProfit = investment.getTotalProfit(month);

		int expectedAccumulatedTotalProfit = 12_274_950;
		assertEquals(expectedAccumulatedTotalProfit, accumulatedTotalProfit);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void shouldThrowExceptionForGetAccumulatedTotalProfit_whenInvalidMonth(int month) {
		assertThrows(IllegalArgumentException.class, () -> investment.getTotalProfit(month));
	}

	@Test
	void shouldReturnFinalMonth() {
		int finalMonth = investment.getFinalMonth();

		int expectedFinalMonth = 12;
		assertEquals(expectedFinalMonth, finalMonth);
	}
}
