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

	public static Stream<Arguments> fixedInstallmentInvestmentSavingMonthSource() {
		List<Arguments> arguments = new ArrayList<>();
		for (int month = 1; month <= 12; month++) {
			int expectedPrincipal = month * 1_000_000;
			arguments.add(Arguments.of(month, expectedPrincipal));
		}
		return arguments.stream();
	}

	public static Stream<Arguments> fixedInstallmentInvestmentSavingMonthAndInterestSource() {
		return Stream.of(
			// month, principal, interest, tax
			Arguments.of(1, 1_000_000, 4_167, 642),
			Arguments.of(2, 2_000_000, 12_500, 1_925),
			Arguments.of(3, 3_000_000, 25_000, 3_850),
			Arguments.of(4, 4_000_000, 41_667, 6_417),
			Arguments.of(5, 5_000_000, 62_500, 9_625),
			Arguments.of(6, 6_000_000, 87_500, 13_475),
			Arguments.of(7, 7_000_000, 116_667, 17_967),
			Arguments.of(8, 8_000_000, 150_000, 23_100),
			Arguments.of(9, 9_000_000, 187_500, 28_875),
			Arguments.of(10, 10_000_000, 229_167, 35_292),
			Arguments.of(11, 11_000_000, 275_000, 42_350),
			Arguments.of(12, 12_000_000, 325_000, 50_050)
		);
	}

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

	@Test
	void shouldReturnPrincipalAmount_whenInvestPeriodIsExpiration() {
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

	@ParameterizedTest
	@MethodSource(value = "fixedInstallmentInvestmentSavingMonthAndInterestSource")
	void shouldReturnAccumulatedInterest(int month, int ignoredPrincipal, int expectedInterest) {
		int interest = investment.getInterest(month);

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
	@MethodSource(value = "fixedInstallmentInvestmentSavingMonthAndInterestSource")
	void shouldReturnTax_givenMonth(int month, int ignoredPrincipal, int ignoredInterest, int expectedTax) {
		int tax = investment.getTax(month);

		assertEquals(expectedTax, tax);
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
