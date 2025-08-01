package co.invest72.investment.domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.TaxableFactory;

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
