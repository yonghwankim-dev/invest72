package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.amount.FixedDepositAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.MonthlyInvestPeriod;
import domain.invest_period.PeriodMonthsRange;
import domain.investment.CompoundFixedDeposit;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedDeposit;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.tax.FixedTaxRate;
import domain.tax.StandardTax;

class ExpirationPeriodBasedInvestmentCalculatorTest {

	private PeriodBasedInvestmentCalculator calculator;

	private void assertPrincipal(int expectedPrincipal, int principal) {
		Assertions.assertEquals(expectedPrincipal, principal, "원금 계산이 잘못되었습니다.");
	}

	@BeforeEach
	void setUp() {
		calculator = new ExpirationPeriodBasedInvestmentCalculator();
	}

	@Test
	void created() {
		PeriodBasedInvestmentCalculator calculator = new ExpirationPeriodBasedInvestmentCalculator();

		Assertions.assertNotNull(calculator);
	}

	@Test
	void shouldReturnPrincipal_whenInvestmentIsSimpleFixedDeposit() {
		Investment investment = new SimpleFixedDeposit(
			new FixedDepositAmount(1_000_000),
			new MonthBasedRemainingPeriodProvider(new PeriodMonthsRange(12)),
			new AnnualInterestRate(0.05),
			new StandardTax(new FixedTaxRate(0.154))
		);

		int principal = calculator.calPrincipal(investment);

		int expectedPrincipal = 1_000_000;
		assertPrincipal(expectedPrincipal, principal);
	}

	@Test
	void shouldReturnPrincipal_whenInvestmentIsCompoundFixedDeposit() {
		Investment investment = new CompoundFixedDeposit(
			new FixedDepositAmount(1_000_000),
			new MonthlyInvestPeriod(12),
			new AnnualInterestRate(0.05),
			new StandardTax(new FixedTaxRate(0.154))
		);

		int principal = calculator.calPrincipal(investment);

		int expectedPrincipal = 1_000_000;
		assertPrincipal(expectedPrincipal, principal);
	}

	@Test
	void shouldReturnPrincipal_whenInvestmentIsSimpleFixedInstallmentSaving() {
		Investment investment = new SimpleFixedInstallmentSaving(
			new MonthlyInstallmentInvestmentAmount(1_000_000),
			new MonthlyInvestPeriod(12),
			new AnnualInterestRate(0.05),
			new StandardTax(new FixedTaxRate(0.154))
		);

		int principal = calculator.calPrincipal(investment);

		int expectedPrincipal = 12_000_000;
		assertPrincipal(expectedPrincipal, principal);
	}

	@Test
	void shouldReturnPrincipal_whenInvestmentIsCompoundFixedInstallmentSaving() {
		Investment investment = new CompoundFixedInstallmentSaving(
			new MonthlyInstallmentInvestmentAmount(1_000_000),
			new MonthlyInvestPeriod(12),
			new AnnualInterestRate(0.05),
			new StandardTax(new FixedTaxRate(0.154))
		);

		int principal = calculator.calPrincipal(investment);

		int expectedPrincipal = 12_000_000;
		assertPrincipal(expectedPrincipal, principal);
	}
}
