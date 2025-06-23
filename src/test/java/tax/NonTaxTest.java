package tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tax.Taxable;
import tax.factory.KoreanTaxableFactory;
import tax.factory.TaxableFactory;

class NonTaxTest {

	private TaxableFactory taxableFactory;

	@BeforeEach
	void setUp() {
		taxableFactory = new KoreanTaxableFactory();
	}

	@Test
	void calculateInterestTaxation(){
		Taxable taxable = taxableFactory.createNonTax();
		int preTaxInterest = 35_929_289;
		int amount = taxable.applyTax(preTaxInterest);

		int expectedAmount = 0;
		assertEquals(expectedAmount, amount, "The amount for tax.NonTax should be 0.");
	}
}
