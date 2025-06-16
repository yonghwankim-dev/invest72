import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompoundInterestTest {

	private InstallmentInvestmentAmount investmentAmount; // 월 투자 금액(원)
	private InvestPeriod investPeriod; // 투자 기간
	private InterestRate annualInterestRateRate; // 연 수익율
	private TaxableFactory taxableFactory;
	private Taxable taxable; // 세금 적용 방식
	private Interest interest;

	private void assertTotalPrincipal(int expectedPrincipal, Interest interest) {
		Assertions.assertEquals(expectedPrincipal, interest.getTotalPrincipal());
	}

	private void assertInterest(int expectedInterest, Interest interest) {
		Assertions.assertEquals(expectedInterest, interest.getInterestAmount());
	}

	private void assertBalanceValue(int expectedBalanceValue, Interest interest) {
		Assertions.assertEquals(expectedBalanceValue, interest.getAmount());
	}

	private void assertTax(int expectedTax, Interest interest) {
		Assertions.assertEquals(expectedTax, interest.getTax());
	}

	private void assertBalanceValue(int expectedBalanceValue, int actualBalanceValue) {
		Assertions.assertEquals(expectedBalanceValue, actualBalanceValue);
	}

	@BeforeEach
	void setUp() {
		investmentAmount = new MonthlyInvestmentAmount(1_000_000);
		investPeriod = new MonthlyInvestPeriod(12);
		annualInterestRateRate = new AnnualInterestRate(0.05);
		taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

	}

	@Test
	void created(){
		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);
		Assertions.assertNotNull(interest);
	}

	@Test
	void shouldReturnBalance(){
		int expectedTotalPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		int expectedBalanceValue = expectedTotalPrincipal + expectedInterest;
		assertTotalPrincipal(expectedTotalPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertBalanceValue(expectedBalanceValue, interest);
	}

	@Test
	void shouldReturnBalance_whenInvestmentPeriodIs6(){
		investPeriod = new MonthlyInvestPeriod(6);
		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		int expectedTotalPrincipal = 6_000_000;
		int expectedInterest = 88_110;
		int expectedBalanceValue = expectedTotalPrincipal + expectedInterest;
		assertTotalPrincipal(expectedTotalPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertBalanceValue(expectedBalanceValue, interest);
	}

	@Test
	void shouldReturnZero_whenMonthlyInvestmentIsZero(){
		investmentAmount = new MonthlyInvestmentAmount(0);
		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		int expectedTotalPrincipal = 0;
		int expectedInterest = 0;
		int expectedBalanceValue = 0;
		assertTotalPrincipal(expectedTotalPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertBalanceValue(expectedBalanceValue, interest);
	}

	@Test
	void shouldReturnBalance_whenInvestmentPeriodIs0(){
		investPeriod = new MonthlyInvestPeriod(0);

		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		int expectedTotalPrincipal = 0;
		int expectedInterest = 0;
		int expectedBalanceValue = 0;
		assertTotalPrincipal(expectedTotalPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertBalanceValue(expectedBalanceValue, interest);
	}

	@Test
	void shouldReturnBalance_whenAnnualInterestRateIsZero(){
		annualInterestRateRate = new AnnualInterestRate(0.0);

		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		int expectedTotalPrincipal = 12_000_000;
		int expectedInterest = 0;
		int expectedBalanceValue = 12_000_000;
		assertTotalPrincipal(expectedTotalPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertBalanceValue(expectedBalanceValue, interest);
	}

	@Test
	void shouldReturnTaxedBalance_whenTaxTypeIsTaxable(){
		int months = 120; // 10년
		investPeriod = new MonthlyInvestPeriod(months);
		taxable = taxableFactory.createStandardTax();
		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		int balanceValue = interest.getAmount();

		int expectedTotalPrincipal = 120_000_000;
		int expectedInterest = 35_929_288;
		int expectedTax = 5_533_110;
	    int expectedBalanceValue = 150_396_178;
		assertTotalPrincipal(expectedTotalPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertTax(expectedTax, interest);
		assertBalanceValue(expectedBalanceValue, balanceValue);
	}

	@Test
	void shouldReturnAmount_whenInvestmentAmountIsYearly(){
		investmentAmount = new YearlyInvestmentAmount(12_000_000);
		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		int amount = interest.getAmount();

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		int expectedAmount = expectedPrincipal + expectedInterest;
		assertTotalPrincipal(expectedPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertBalanceValue(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenInvestPeriodIsYearly() {
		investPeriod = new YearlyInvestPeriod(10);
		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		int amount = interest.getAmount();

		int expectedPrincipal = 120_000_000;
		int expectedInterest = 35_929_288;
		int expectedAmount = 155_929_288;
		assertTotalPrincipal(expectedPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertBalanceValue(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenTaxableIsTaxBenefit(){
		taxable = taxableFactory.createTaxBenefit(0.014);
		interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);

		int amount = interest.getAmount();

		int expectedPrincipal = 12_000_000;
		int expectedInterest = 330_017;
		int expectedTax = 4_620;
		int expectedAmount = 12_325_397;
		assertTotalPrincipal(expectedPrincipal, interest);
		assertInterest(expectedInterest, interest);
		assertTax(expectedTax, interest);
		assertBalanceValue(expectedAmount, amount);
	}
}
