package domain;

import org.junit.jupiter.api.Assertions;
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
	@Test
	void created() {
		InvestmentCalculator calculator = new ExpirationInvestmentCalculator();

		Assertions.assertNotNull(calculator);
	}

	@Test
	void shouldReturnPrincipal_whenInvestmentIsSimpleFixedDeposit() {
		InvestmentCalculator calculator = new ExpirationInvestmentCalculator();
		ExpirationInvestment investment = new SimpleFixedDeposit(
			new FixedDepositAmount(1_000_000), // 투자 금액
			new MonthBasedRemainingPeriodProvider(new PeriodMonthsRange(12)), // 12개월 후 만기
			new AnnualInterestRate(0.05), // 연이율 5%
			new StandardTax(new FixedTaxRate(0.154)) // 세금 15.4%
		);

		int principal = calculator.calPrincipal(investment);

		int expectedPrincipal = 1_000_000; // 원금은 투자 금액과 동일
		Assertions.assertEquals(expectedPrincipal, principal, "원금 계산이 잘못되었습니다.");
	}

	@Test
	void shouldReturnPrincipal_whenInvestmentIsCompoundFixedDeposit() {
		InvestmentCalculator calculator = new ExpirationInvestmentCalculator();
		ExpirationInvestment investment = new CompoundFixedDeposit(
			new FixedDepositAmount(1_000_000), // 투자 금액
			new MonthlyInvestPeriod(12), // 12개월 후 만기
			new AnnualInterestRate(0.05), // 연이율 5%
			new StandardTax(new FixedTaxRate(0.154)) // 세금 15.4%
		);

		int principal = calculator.calPrincipal(investment);

		int expectedPrincipal = 1_000_000; // 원금은 투자 금액과 동일
		Assertions.assertEquals(expectedPrincipal, principal, "원금 계산이 잘못되었습니다.");
	}
}
