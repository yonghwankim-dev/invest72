package domain.investment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import domain.interest_rate.AnnualInterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodYearRange;
import domain.tax.factory.KoreanTaxableFactory;

class MonthlyInvestmentTest {

	@Test
	void created() {
		MonthlyInvestment monthlyInvestment = new SimpleFixedDeposit(
			new FixedDepositAmount(1_000_000),
			new MonthBasedRemainingPeriodProvider(new PeriodYearRange(1)),
			new AnnualInterestRate(0.05),
			new KoreanTaxableFactory().createNonTax()
		);
		Assertions.assertNotNull(monthlyInvestment);
	}

	@Test
	void getPrincipalAmount_whenMonthIs1() {
		MonthlyInvestment monthlyInvestment = new SimpleFixedDeposit(
			new FixedDepositAmount(1_000_000),
			new MonthBasedRemainingPeriodProvider(new PeriodYearRange(1)),
			new AnnualInterestRate(0.05),
			new KoreanTaxableFactory().createNonTax()
		);

		int principalAmount = monthlyInvestment.getPrincipalAmount(1);

		Assertions.assertEquals(1_000_000, principalAmount);
	}

	@ParameterizedTest
	@CsvSource({"0", "13"})
	void getPrincipalAmount_whenInvalidMonth_shouldThrowException(int month) {
		MonthlyInvestment monthlyInvestment = new SimpleFixedDeposit(
			new FixedDepositAmount(1_000_000),
			new MonthBasedRemainingPeriodProvider(new PeriodYearRange(1)),
			new AnnualInterestRate(0.05),
			new KoreanTaxableFactory().createNonTax()
		);

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			monthlyInvestment.getPrincipalAmount(month);
		});
	}
}
