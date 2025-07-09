package domain.investment;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.interest_rate.AnnualInterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodYearRange;
import domain.invest_period.YearlyInvestPeriod;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;

class MonthlyInvestmentTest {

	private MonthlyInvestment monthlyInvestment;
	private FixedDepositAmount investmentAmount;
	private MonthBasedRemainingPeriodProvider remainingPeriodProvider;
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

	@BeforeEach
	void setUp() {
		investmentAmount = new FixedDepositAmount(1_000_000);
		remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(
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
	void getPrincipalAmount_whenValidMonth(int month) {
		int principalAmount = monthlyInvestment.getPrincipalAmount(month);

		Assertions.assertEquals(1_000_000, principalAmount);
	}

	@ParameterizedTest
	@MethodSource(value = "invalidInterestSource")
	void getPrincipalAmount_whenInvalidMonth_shouldThrowException(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getPrincipalAmount(month);
		});
	}

	@ParameterizedTest
	@MethodSource(value = "interestSource")
	void getInterest_whenValidMonth(int month, int expectedInterest) {
		int interest = monthlyInvestment.getInterest(month);

		Assertions.assertEquals(expectedInterest, interest);
	}

	@ParameterizedTest
	@MethodSource(value = "invalidInterestSource")
	void getInterest_whenInvalidMonth_shouldThrowException(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getInterest(month);
		});
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
	void getTax_whenMonthIsValid(int month) {
		int tax = monthlyInvestment.getTax(month);

		Assertions.assertEquals(0, tax);
	}

	@ParameterizedTest
	@ValueSource(ints = {-1, 0, 13})
	void getTax_whenMonthIsInvalid(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getTax(month);
		});
	}

	@ParameterizedTest
	@MethodSource(value = "totalProfitSource")
	void getTotalProfit_whenMonthIsValid(int month, int expectedTotalProfit) {
		int totalProfit = monthlyInvestment.getTotalProfit(month);

		Assertions.assertEquals(expectedTotalProfit, totalProfit);
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
	void getPrincipalAmount_whenInstanceOfCompoundFixedDeposit(int month) {
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		monthlyInvestment = new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
		int principalAmount = monthlyInvestment.getPrincipalAmount(month);

		Assertions.assertEquals(1_000_000, principalAmount);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 13})
	void getPrincipalAmount_shouldThrowException_whenInvalidMonth(int month) {
		InvestPeriod investPeriod = new YearlyInvestPeriod(1);
		monthlyInvestment = new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getPrincipalAmount(month);
		});
	}
}
