import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleFixedDepositTest {

	private Investment investment;
	private LumpSumInvestmentAmount investmentAmount;
	private Taxable taxable;
	private InterestRate interestRate;
	private InvestPeriod investPeriod;

	@BeforeEach
	void setUp() {
		investmentAmount = new FixedDepositAmount(1_000_000);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
		interestRate = new AnnualInterestRate(0.05);
		investPeriod = new YearlyInvestPeriod(1);
		investment = new SimpleFixedDeposit(investmentAmount, interestRate, investPeriod, taxable);
	}

	@Test
	void created(){
		assertNotNull(investment);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsSimple(){
		int amount = investment.getAmount();

		int expectedAmount = 1_050_000;
		assertEquals(expectedAmount, amount);
	}
}
