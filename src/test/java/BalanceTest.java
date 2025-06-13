import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceTest {

	private InvestmentAmount monthlyInvestment; // 월 투자 금액(원)
	private InvestPeriod investPeriod; // 투자 기간
	private InterestRate annualInterestRateRate; // 연 수익율
	private Balance balance;


	private void assertPrincipal(int expectedPrincipal, Balance balance) {
		Assertions.assertEquals(expectedPrincipal, balance.getTotalPrincipal());
	}

	private void assertInterest(int expectedInterest, Balance balance) {
		Assertions.assertEquals(expectedInterest, balance.getInterestAmount());
	}

	@BeforeEach
	void setUp() {
		monthlyInvestment = new MonthlyInvestmentAmount(1_000_000);
		investPeriod = new MonthlyInvestPeriod(12);
		annualInterestRateRate = new AnnualInterestRate(0.05);
		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);
	}

	@Test
	void created(){
		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);
		Assertions.assertNotNull(balance);
	}

	@Test
	void shouldReturnSummary(){
		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
	}

	@Test
	void shouldReturnSummary_whenInvestmentPeriodIs6(){
		investPeriod = new MonthlyInvestPeriod(6);
		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 6_000_000;
		int expectedInterest = 88_110;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
	}

	@Test
	void shouldReturnZero_whenMonthlyInvestmentIsZero(){
		monthlyInvestment = new MonthlyInvestmentAmount(0);
		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
	}

	@Test
	void shouldReturnSummary_whenInvestmentPeriodIs0(){
		investPeriod = new MonthlyInvestPeriod(0);

		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
	}

	@Test
	void shouldReturnSummary_whenAnnualInterestRateIsZero(){
		annualInterestRateRate = new AnnualInterestRate(0.0);

		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 0;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
	}
}
