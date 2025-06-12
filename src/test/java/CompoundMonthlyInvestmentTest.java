import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompoundMonthlyInvestmentTest {

	private Investment calculator;
	private InvestmentAmount monthlyInvestment; // 월 투자 금액(원)
	private InvestPeriod investPeriod; // 투자 기간
	private InterestRate annualInterestRateRate; // 연 수익율
	private Balance balance;

	private void assertInvestmentSummary(InvestmentSummary expected, Balance balance) {
		Assertions.assertEquals(expected.getPrincipal(), balance.getTotalPrincipal());
		Assertions.assertEquals(expected.getInterest(), balance.getInterestAmount());
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
		balance = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, balance);
	}

	@Test
	void shouldReturnSummary_whenInvestmentPeriodIs6(){
		investPeriod = new MonthlyInvestPeriod(6);

		balance = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 6_000_000;
		int expectedInterest = 88_110;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, balance);
	}

	@Test
	void shouldReturnZero_whenMonthlyInvestmentIsZero(){
		monthlyInvestment = new MonthlyInvestmentAmount(0);

		balance = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, balance);
	}

	@Test
	void shouldReturnSummary_whenInvestmentPeriodIs0(){
		investPeriod = new MonthlyInvestPeriod(0);

		balance = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, balance);
	}

	@Test
	void shouldReturnSummary_whenAnnualInterestRateIsZero(){
		annualInterestRateRate = new AnnualInterestRate(0.0);

		balance = calculator.calculate(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 0;
		InvestmentSummary expected = new CompoundMonthlyInvestmentSummary(expectedPrincipal, expectedInterest);
		assertInvestmentSummary(expected, balance);
	}
}
