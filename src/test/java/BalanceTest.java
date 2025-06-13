import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

	private void assertTaxable(int expectedTaxable, Balance balance) {
		Assertions.assertEquals(expectedTaxable, balance.getTaxableAmount());
	}

	private void assertTaxable(int expectedTaxable, int actualTaxable) {
		Assertions.assertEquals(expectedTaxable, actualTaxable);
	}

	private void assertBalanceValue(int expectedBalanceValue, Balance balance) {
		Assertions.assertEquals(expectedBalanceValue, balance.getBalanceValue());
	}

	private void assertBalanceValue(int expectedBalanceValue, int actualBalanceValue) {
		Assertions.assertEquals(expectedBalanceValue, actualBalanceValue);
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
	void shouldReturnBalance(){
		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		int expectedBalanceValue = expectedPrincipal + expectedInterest;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
		assertBalanceValue(expectedBalanceValue, balance);
	}

	@Test
	void shouldReturnBalance_whenInvestmentPeriodIs6(){
		investPeriod = new MonthlyInvestPeriod(6);
		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 6_000_000;
		int expectedInterest = 88_110;
		int expectedBalanceValue = expectedPrincipal + expectedInterest;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
		assertBalanceValue(expectedBalanceValue, balance);
	}

	@Test
	void shouldReturnZero_whenMonthlyInvestmentIsZero(){
		monthlyInvestment = new MonthlyInvestmentAmount(0);
		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		int expectedBalanceValue = 0;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
		assertBalanceValue(expectedBalanceValue, balance);
	}

	@Test
	void shouldReturnBalance_whenInvestmentPeriodIs0(){
		investPeriod = new MonthlyInvestPeriod(0);

		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 0;
		int expectedInterest = 0;
		int expectedBalanceValue = 0;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
		assertBalanceValue(expectedBalanceValue, balance);
	}

	@Test
	void shouldReturnBalance_whenAnnualInterestRateIsZero(){
		annualInterestRateRate = new AnnualInterestRate(0.0);

		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 0;
		int expectedBalanceValue = expectedPrincipal + expectedInterest;
		assertPrincipal(expectedPrincipal, balance);
		assertInterest(expectedInterest, balance);
		assertBalanceValue(expectedBalanceValue, balance);
	}

	@Test
	void shouldReturnBalance_whenTaxTypeIsTaxable() {
		// given
		int months = 120; // 10년
		investPeriod = new MonthlyInvestPeriod(months);
		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);
		// when
		int taxableAmount = balance.getTaxableAmount();
		// then
		int expectedTaxable = 5_533_110;
		assertTaxable(expectedTaxable, taxableAmount);
	}

	@Test
	void shouldReturnBalanceValue_whenTaxTypeIsTaxable(){
	    // given
		int months = 120; // 10년
		investPeriod = new MonthlyInvestPeriod(months);
		balance = new CompoundBalance(monthlyInvestment, investPeriod, annualInterestRateRate);
	    // when
		int balanceValue = balance.getBalanceValue();
		// then
	    int expectedBalanceValue = 150_396_178;
		assertBalanceValue(expectedBalanceValue, balanceValue);
	}
}
