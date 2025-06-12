import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvestmentCalculatorTest {

	private InvestmentCalculator calculator;
	private int monthlyInvestment; // 월 투자 금액(원)
	private int investmentPeriod; // 투자 기간
	private double annualInterestRate; // 연 수익율

	private void assertInvestmentSummary(InvestmentSummary expected, InvestmentSummary actual) {
		Assertions.assertEquals(expected.getPrincipal(), actual.getPrincipal());
		Assertions.assertEquals(expected.getInterest(), actual.getInterest());
	}

	@BeforeEach
	void setUp() {
		calculator = new CompoundMonthlyInvestmentCalculator();
		monthlyInvestment = 1_000_000;
		investmentPeriod = 12;
		annualInterestRate = 0.05;
	}

	@Test
	void created(){
		Assertions.assertNotNull(calculator);
	}

	@Test
	void shouldReturnSummary(){
		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}

	@Test
	void shouldReturnSummary_whenInvestmentPeriodIs6(){
		investmentPeriod = 6;

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate);

		int expectedPrincipal = 6_000_000;
		int expectedInterest = 88_110;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}

	@Test
	void shouldReturnZero_whenMonthlyInvestmentIsZero(){
		monthlyInvestment = 0;

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}

	@Test
	void shouldThrowException_whenMonthlyInvestmentIsNegative() {
		monthlyInvestment = -1_000_000;

		Assertions.assertThrows(IllegalArgumentException.class,
			() -> calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate));
	}

	@Test
	void shouldReturnSummary_whenInvestmentPeriodIs0(){
		investmentPeriod = 0;

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}

	@Test
	void shouldThrowException_whenInvestmentPeriodIsNegative(){
		investmentPeriod = -1;

		Assertions.assertThrows(IllegalArgumentException.class,
			() -> calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate));
	}

	@Test
	void shouldReturnSummary_whenAnnualInterestRateIsZero(){
		annualInterestRate = 0.0;

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investmentPeriod, annualInterestRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}
}
