import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompoundMonthlyInvestmentTest {

	private Investment calculator;
	private InvestmentAmount monthlyInvestment; // 월 투자 금액(원)
	private InvestPeriod investPeriod; // 투자 기간
	private InterestRate annualInterestRateRate; // 연 수익율

	private void assertInvestmentSummary(InvestmentSummary expected, InvestmentSummary actual) {
		Assertions.assertEquals(expected.getPrincipal(), actual.getPrincipal());
		Assertions.assertEquals(expected.getInterest(), actual.getInterest());
	}

	@BeforeEach
	void setUp() {
		calculator = new CompoundMonthlyInvestment();
		monthlyInvestment = new MonthlyInvestmentAmount(1_000_000);
		investPeriod = new MonthlyInvestPeriod(12);
		annualInterestRateRate = new AnnualInterestRate(0.05);
	}

	@Test
	void created(){
		Assertions.assertNotNull(calculator);
	}

	@Test
	void shouldReturnSummary(){
		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}

	@Test
	void shouldReturnSummary_whenInvestmentPeriodIs6(){
		investPeriod = new MonthlyInvestPeriod(6);

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 6_000_000;
		int expectedInterest = 88_110;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}

	@Test
	void shouldReturnZero_whenMonthlyInvestmentIsZero(){
		monthlyInvestment = new MonthlyInvestmentAmount(0);

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}

	@Test
	void shouldReturnSummary_whenInvestmentPeriodIs0(){
		investPeriod = new MonthlyInvestPeriod(0);

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}

	@Test
	void shouldReturnSummary_whenAnnualInterestRateIsZero(){
		annualInterestRateRate = new AnnualInterestRate(0.0);

		InvestmentSummary summary = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, summary);
	}
}
