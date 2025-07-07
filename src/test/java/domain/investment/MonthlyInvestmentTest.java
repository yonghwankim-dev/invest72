package domain.investment;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import domain.interest_rate.AnnualInterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodYearRange;
import domain.tax.factory.KoreanTaxableFactory;

class MonthlyInvestmentTest {

	private MonthlyInvestment monthlyInvestment;

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

	@BeforeEach
	void setUp() {
		monthlyInvestment = new SimpleFixedDeposit(
			new FixedDepositAmount(1_000_000),
			new MonthBasedRemainingPeriodProvider(new PeriodYearRange(1)),
			new AnnualInterestRate(0.05),
			new KoreanTaxableFactory().createNonTax()
		);
	}

	@ParameterizedTest
	@CsvSource({"1", "12"})
	void getPrincipalAmount_whenValidMonth(int month) {
		int principalAmount = monthlyInvestment.getPrincipalAmount(month);

		Assertions.assertEquals(1_000_000, principalAmount);
	}

	@ParameterizedTest
	@CsvSource({"-1", "0", "13"})
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
	@CsvSource({"-1", "0", "13"})
	void getInterest_whenInvalidMonth_shouldThrowException(int month) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getInterest(month);
		});
	}
}
