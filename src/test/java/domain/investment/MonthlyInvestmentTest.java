package domain.investment;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.amount.FixedDepositAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodYearRange;
import domain.invest_period.YearlyInvestPeriod;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;

class MonthlyInvestmentTest {

	private MonthlyInvestment monthlyInvestment;
	private FixedDepositAmount investmentAmount;
	private AnnualInterestRate interestRate;
	private Taxable taxable;

	public static Stream<Arguments> interestSource() {
		return Stream.of(
			Arguments.of(1, 4_166),
			Arguments.of(2, 8_332),
			Arguments.of(3, 12_498),
			Arguments.of(4, 16_664),
			Arguments.of(5, 20_830),
			Arguments.of(6, 24_996),
			Arguments.of(7, 29_162),
			Arguments.of(8, 33_328),
			Arguments.of(9, 37_494),
			Arguments.of(10, 41_660),
			Arguments.of(11, 45_826),
			Arguments.of(12, 49_992)
		);
	}

	public static Stream<Arguments> invalidInterestSource() {
		return Stream.of(
			Arguments.of(-1),
			Arguments.of(0),
			Arguments.of(13)
		);
	}

	public static Stream<Arguments> totalProfitSource() {
		return Stream.of(
			Arguments.of(1, 1_004_166),
			Arguments.of(2, 1_008_332),
			Arguments.of(3, 1_012_498),
			Arguments.of(4, 1_016_664),
			Arguments.of(5, 1_020_830),
			Arguments.of(6, 1_024_996),
			Arguments.of(7, 1_029_162),
			Arguments.of(8, 1_033_328),
			Arguments.of(9, 1_037_494),
			Arguments.of(10, 1_041_660),
			Arguments.of(11, 1_045_826),
			Arguments.of(12, 1_049_992)
		);
	}

	private void assertAccumulatedInterest(int expectedAccumulatedInterest, int month) {
		Assertions.assertEquals(expectedAccumulatedInterest, monthlyInvestment.getInterest(month));
	}

	private void assertAccumulatedTax(int month, int expectedTax) {
		Assertions.assertEquals(expectedTax, monthlyInvestment.getAccumulatedTax(month));
	}

	private void assertAccumulatedTotalProfit(int month, int expectedAccumulatedTotalProfit) {
		Assertions.assertEquals(expectedAccumulatedTotalProfit, monthlyInvestment.getAccumulatedTotalProfit(month));
	}

	@BeforeEach
	void setUp() {
		investmentAmount = new FixedDepositAmount(1_000_000);
		MonthBasedRemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(
			new PeriodYearRange(1));
		interestRate = new AnnualInterestRate(0.05);
		taxable = new KoreanTaxableFactory().createNonTax();
		monthlyInvestment = new SimpleFixedDeposit(
			investmentAmount,
			remainingPeriodProvider,
			interestRate,
			taxable
		);
	}

	@ParameterizedTest
	@CsvSource({"1", "12"})
	void getAccumulatedPrincipal_whenValidMonth(int month) {
		Assertions.assertEquals(1_000_000, monthlyInvestment.getPrincipal(month));
	}

	@ParameterizedTest
	@MethodSource(value = "invalidInterestSource")
	void getAccumulatedPrincipal_whenInvalidMonth_shouldThrowException(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getPrincipal(month);
		});
	}

	@ParameterizedTest
	@MethodSource(value = "interestSource")
	void getAccumulatedInterest_whenValidMonth(int month, int expectedAccumulatedInterest) {
		int accumulatedInterest = monthlyInvestment.getInterest(month);

		Assertions.assertEquals(expectedAccumulatedInterest, accumulatedInterest);
	}

	@ParameterizedTest
	@MethodSource(value = "invalidInterestSource")
	void getAccumulatedInterest_whenInvalidMonth_shouldThrowException(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getInterest(month);
		});
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
	void getAccumulatedTax_whenMonthIsValid(int month) {
		int tax = monthlyInvestment.getAccumulatedTax(month);

		Assertions.assertEquals(0, tax);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 13})
	void getAccumulatedTax_whenMonthIsInvalid(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getAccumulatedTax(month);
		});
	}

	@ParameterizedTest
	@MethodSource(value = "totalProfitSource")
	void getAccumulatedTotalProfit_whenMonthIsValid(int month, int expectedTotalProfit) {
		int totalProfit = monthlyInvestment.getAccumulatedTotalProfit(month);

		Assertions.assertEquals(expectedTotalProfit, totalProfit);
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
	void getAccumulatedPrincipal_whenInstanceOfCompoundFixedDeposit(int month) {
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		monthlyInvestment = new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
		int principalAmount = monthlyInvestment.getPrincipal(month);

		Assertions.assertEquals(1_000_000, principalAmount);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void getAccumulatedPrincipal_shouldThrowException_whenInvalidMonth(int month) {
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		monthlyInvestment = new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getPrincipal(month);
		});
	}

	@Test
	void getAccumulatedInterest_shouldReturnAccumulatedInterest() {
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		monthlyInvestment = new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
		assertAccumulatedInterest(4_166, 1);
		assertAccumulatedInterest(8_349, 2);
		assertAccumulatedInterest(12_549, 3);
		assertAccumulatedInterest(16_767, 4);
		assertAccumulatedInterest(21_002, 5);
		assertAccumulatedInterest(51_147, 12);
	}

	@Test
	void getAccumulatedTax_shouldReturnTax_whenTaxIsStandard() {
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		taxable = new KoreanTaxableFactory().createStandardTax(new FixedTaxRate(0.154));
		monthlyInvestment = new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);

		assertAccumulatedTax(1, 641);
		assertAccumulatedTax(2, 1285);
		assertAccumulatedTax(3, 1932);
		assertAccumulatedTax(12, 7876);
	}

	@Test
	void getAccumulatedTax_shouldReturnZero_whenTaxIsNonTax() {
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		taxable = new KoreanTaxableFactory().createNonTax();
		monthlyInvestment = new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);

		assertAccumulatedTax(1, 0);
		assertAccumulatedTax(2, 0);
		assertAccumulatedTax(3, 0);
		assertAccumulatedTax(12, 0);
	}

	@Test
	void shouldReturnAccumulatedTotalProfit_whenTaxIsStandard() {
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		taxable = new KoreanTaxableFactory().createStandardTax(new FixedTaxRate(0.154));
		monthlyInvestment = new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);

		assertAccumulatedTotalProfit(1, 1_003_525);
		assertAccumulatedTotalProfit(2, 1_007_064);
		assertAccumulatedTotalProfit(3, 1_010_617);
	}
}
