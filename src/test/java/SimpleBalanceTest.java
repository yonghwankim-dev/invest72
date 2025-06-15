import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleBalanceTest {

	private Balance balance;
	private InvestmentAmount monthlyInvestment;
	private InvestPeriod investPeriod;
	private InterestRate annualInterestRateRate;
	private TaxableFactory taxableFactory;
	private Taxable taxable;

	private void assertBalanceValue(int expectedBalanceValue, int actualBalanceValue) {
		Assertions.assertEquals(expectedBalanceValue, actualBalanceValue);
	}

	@BeforeEach
	void setUp() {
		// 월 투자 금액(원)
		monthlyInvestment = new MonthlyInvestmentAmount(1_000_000);
		// 투자 기간
		investPeriod = new MonthlyInvestPeriod(12);
		// 연 수익율
		annualInterestRateRate = new AnnualInterestRate(0.05);
		taxableFactory = new KoreanTaxableFactory();
		// 세금 적용 방식
		taxable = taxableFactory.createNonTax();
		balance = new SimpleBalance(monthlyInvestment, investPeriod, annualInterestRateRate, taxable);
	}

	@Test
	void created() {
		Assertions.assertNotNull(balance);
	}

	@Test
	void shouldReturnBalanceAmount(){
		int amount = balance.getAmount();

		int expectedAmount = 12_325_000;
		assertBalanceValue(expectedAmount, amount);
	}

	@Test
	void shouldReturnBalanceAmount_whenTaxIsStandardTax(){
		taxable = taxableFactory.createStandardTax();
		balance = new SimpleBalance(monthlyInvestment, investPeriod, annualInterestRateRate, taxable);

		int amount = balance.getAmount();

		int expectedAmount = 12_274_950;
		assertBalanceValue(expectedAmount, amount);
	}
}
