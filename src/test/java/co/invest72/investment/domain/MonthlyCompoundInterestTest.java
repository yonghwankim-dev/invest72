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
import co.invest72.investment.domain.tax.KoreanTaxableFactory;

class MonthlyCompoundInterestTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
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

	@ParameterizedTest
	@MethodSource(value = "source.TestDataProvider#getInterestWithMonthSource")
	void getInterest_whenValidMonth(int month, int expectedInterest) {
		int interest = investment.getInterest(month);

		Assertions.assertThat(interest).isEqualTo(expectedInterest);
	}
}
