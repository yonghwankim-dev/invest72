import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixedDepositTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		investment = new FixedDeposit(investmentAmount, taxable);
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
