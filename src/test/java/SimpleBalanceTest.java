import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleBalanceTest {

	private Balance balance;

	private void assertBalanceValue(int expectedBalanceValue, int actualBalanceValue) {
		Assertions.assertEquals(expectedBalanceValue, actualBalanceValue);
	}

	@BeforeEach
	void setUp() {
		// 월 투자 금액(원)
		InvestmentAmount monthlyInvestment = new MonthlyInvestmentAmount(1_000_000);
		// 투자 기간
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		// 연 수익율
		InterestRate annualInterestRateRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		// 세금 적용 방식
		Taxable taxable = taxableFactory.createNonTax();
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
}
