package co.invest72.investment.domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.TaxableFactory;

class NonTaxTest {

	private TaxableFactory taxableFactory;

	@BeforeEach
	void setUp() {
		taxableFactory = new KoreanTaxableFactory();
	}

	@Test
	void calculateInterestTaxation() {
		Taxable taxable = taxableFactory.createNonTax();
		int preTaxInterest = 35_929_289;
		int amount = taxable.applyTax(preTaxInterest);

		int expectedAmount = 0;
		assertEquals(expectedAmount, amount, "The amount for tax.NonTax should be 0.");
	}
}
