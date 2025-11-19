package co.invest72.investment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.amount.MonthlyAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.YearlyInvestPeriod;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;

class MonthlyCompoundInterestTest {

	private Investment investment;
	private TaxableFactory taxableFactory;

	@BeforeEach
	void setUp() {
		taxableFactory = new KoreanTaxableFactory();
		investment = MonthlyCompoundInterest.builder()
			.initialAmount(new FixedDepositAmount(0))
			.monthlyAmount(new MonthlyAmount(1_000_000))
			.investPeriod(new YearlyInvestPeriod(1))
			.interestRate(new AnnualInterestRate(0.05))
			.taxable(taxableFactory.createNonTax())
			.build();
	}

	@Test
	void canCreated() {
		Assertions.assertThat(investment).isNotNull();
	}

	@Test
	void getPrincipal() {
		int principal = investment.getPrincipal();

		Assertions.assertThat(principal).isEqualTo(11_000_000);
	}

	@ParameterizedTest
	@MethodSource(value = "source.TestDataProvider#getPrincipalWithMonthSource")
	void getPrincipal_whenValidMonth(int month, int expectedPrincipal) {
		int principal = investment.getPrincipal(month);

		Assertions.assertThat(principal).isEqualTo(expectedPrincipal);
	}

	@Test
	void getPrincipal_whenMonthGreaterThanInvestPeriod_thenThrowException() {
		Assertions.assertThatThrownBy(() -> investment.getPrincipal(13))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid month");
	}

	@ParameterizedTest
	@MethodSource(value = "source.TestDataProvider#getInterestWithMonthSource")
	void getInterest_whenValidMonth(int month, int expectedInterest) {
		int interest = investment.getInterest(month);

		Assertions.assertThat(interest).isEqualTo(expectedInterest);
	}

	@Test
	void getInterest_whenMonthGreaterThanInvestPeriod_thenThrowException() {
		Assertions.assertThatThrownBy(() -> investment.getInterest(13))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid month");
	}

	@Test
	void getTax() {
		int tax = investment.getTax();

		Assertions.assertThat(tax).isZero();
	}

	@Test
	void getTax_whenTaxIsStandardTax_thenReturnCalculatedTax() {
		investment = ((MonthlyCompoundInterest)investment).toBuilder()
			.taxable(taxableFactory.createStandardTax(new FixedTaxRate(0.154)))
			.build();

		int tax = investment.getTax();

		Assertions.assertThat(tax).isEqualTo(42_944);
	}

	@ParameterizedTest
	@MethodSource(value = "source.TestDataProvider#getTaxWithMonthSource")
	void getTax_whenValidMonth_thenReturnTax(int month, int expectedTax) {
		investment = ((MonthlyCompoundInterest)investment).toBuilder()
			.taxable(taxableFactory.createStandardTax(new FixedTaxRate(0.154)))
			.build();

		int tax = investment.getTax(month);

		Assertions.assertThat(tax).isEqualTo(expectedTax);
	}

	@Test
	void getTax_whenMonthGreaterThanInvestPeriod_thenThrowException() {
		Assertions.assertThatThrownBy(() -> investment.getTax(13))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid month");
	}

	@Test
	void getTotalProfit() {
		int totalProfit = investment.getTotalProfit();

		Assertions.assertThat(totalProfit).isEqualTo(11_278_855);
	}

	@ParameterizedTest
	@MethodSource(value = "source.TestDataProvider#getTotalProfitWithMonthSource")
	void getTotalProfit_whenValidMonth_thenReturnTotalProfit(int month, int expectedTotalProfit) {
		int totalProfit = investment.getTotalProfit(month);

		Assertions.assertThat(totalProfit).isEqualTo(expectedTotalProfit);
	}

	@Test
	void getTotalProfit_whenMonthGreaterThanInvestPeriod_thenThrowException() {
		Assertions.assertThatThrownBy(() -> investment.getTotalProfit(13))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Invalid month");
	}
}
