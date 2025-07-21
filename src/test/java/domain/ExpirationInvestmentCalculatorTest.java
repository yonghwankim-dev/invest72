package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.amount.FixedDepositAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.MonthlyInvestPeriod;
import domain.invest_period.PeriodMonthsRange;
import domain.investment.CompoundFixedDeposit;
import domain.investment.ExpirationInvestment;
import domain.investment.SimpleFixedDeposit;
import domain.tax.FixedTaxRate;
import domain.tax.StandardTax;

class ExpirationInvestmentCalculatorTest {

	private InvestmentCalculator calculator;

	private void assertPrincipal(int expectedPrincipal, int principal) {
		Assertions.assertEquals(expectedPrincipal, principal, "원금 계산이 잘못되었습니다.");
	}

	@BeforeEach
	void setUp() {
		calculator = new ExpirationInvestmentCalculator();
	}

	@Test
	void created() {
		InvestmentCalculator calculator = new ExpirationInvestmentCalculator();

		Assertions.assertNotNull(calculator);
	}

	@Test
	void shouldReturnPrincipal_whenInvestmentIsSimpleFixedDeposit() {
		ExpirationInvestment investment = new SimpleFixedDeposit(
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
		ExpirationInvestment investment = new CompoundFixedDeposit(
			new FixedDepositAmount(1_000_000),
			new MonthlyInvestPeriod(12),
			new AnnualInterestRate(0.05),
			new StandardTax(new FixedTaxRate(0.154))
		);

		int principal = calculator.calPrincipal(investment);

		int expectedPrincipal = 1_000_000;
		assertPrincipal(expectedPrincipal, principal);
	}
}
