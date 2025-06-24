package domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class StandardTaxTest {

	private TaxableFactory taxableFactory;

	@BeforeEach
	void setUp() {
		taxableFactory = new KoreanTaxableFactory();
	}

	@Test
	void calculateNetInterest() {
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		// 세전 이자
		int preTaxInterest = 35_929_289;
		int amount = taxable.applyTax(preTaxInterest);

		int expectedAmount = 5_533_111;
		assertEquals(expectedAmount, amount);
	}
}
