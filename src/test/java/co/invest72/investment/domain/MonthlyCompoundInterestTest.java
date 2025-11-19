package co.invest72.investment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

	@Test
	void getPrincipal_whenMonthIsZero_thenReturnZeroPrincipal() {
		int principal = investment.getPrincipal(0);

		Assertions.assertThat(principal).isZero();
	}

	@Test
	void getPrincipal_whenMonthIsOne_thenReturnPrincipal() {
		int principal = investment.getPrincipal(1);

		Assertions.assertThat(principal).isZero();
	}
}
