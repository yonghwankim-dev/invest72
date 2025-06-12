import java.time.Period;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvestmentCalculatorTest {

	private InvestmentCalculator calculator;

	@BeforeEach
	void setUp() {
		calculator = new CompoundMonthlyInvestmentCalculator();
	}

	@Test
	void created(){
		Assertions.assertNotNull(calculator);
	}

	@Test
	void shouldReturnZero_whenMonthlyInvestmentIsZero(){
		// 월 투자 금액(원)
		int monthlyInvestment = 0;
		// 투자 기간
		int investmentPeriod = 12; // 12개월
		// 연 수익율
		double annualInterestRate = 0.05; // 5%

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		Assertions.assertEquals(expected.getPrincipal(), summary.getPrincipal());
		Assertions.assertEquals(expected.getInterest(), summary.getInterest());
	}

	@Test
	void shouldReturnPrincipal(){
		// 월 투자 금액(원)
		int monthlyInvestment = 1_000_000;
		// 투자 기간
		int investmentPeriod = 12; // 12개월
		// 연 수익율
		double annualInterestRate = 0.05; // 5%

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		Assertions.assertEquals(expected.getPrincipal(), summary.getPrincipal());
		Assertions.assertEquals(expected.getInterest(), summary.getInterest());
		// todo: refactor
	}
}
