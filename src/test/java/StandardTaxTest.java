import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StandardTaxTest {
	@Test
	void calculateNetInterest(){
		Taxable taxable = new StandardTax(0.154);
		// 세전 이자
		int preTaxInterest = 35_929_289;
		int amount = taxable.calculateInterestTaxation(preTaxInterest);

		int expectedAmount = 5_533_110;
		assertEquals(expectedAmount, amount);
	}
}
