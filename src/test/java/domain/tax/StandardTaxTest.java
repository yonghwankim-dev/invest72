package domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class StandardTaxTest {

	private TaxableFactory taxableFactory;

	public static Stream<Arguments> invalidTaxRateSource() {
		return Stream.of(
			Arguments.of(-0.01),
			Arguments.of(1.0)
		);
	}

	@BeforeEach
	void setUp() {
		taxableFactory = new KoreanTaxableFactory();
	}

	@Test
	void calculateNetInterest() {
		Taxable taxable = taxableFactory.createStandardTax(0.154);
		// 세전 이자
		int preTaxInterest = 35_929_289;
		int amount = taxable.applyTax(preTaxInterest);

		int expectedAmount = 5_533_111;
		assertEquals(expectedAmount, amount);
	}
}
