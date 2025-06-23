package tax;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import tax.StandardTax;
import tax.Taxable;
import tax.factory.KoreanTaxableFactory;
import tax.factory.TaxableFactory;

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
	void calculateNetInterest(){
		Taxable taxable = taxableFactory.createStandardTax();
		// 세전 이자
		int preTaxInterest = 35_929_289;
		int amount = taxable.applyTax(preTaxInterest);

		int expectedAmount = 5_533_111;
		assertEquals(expectedAmount, amount);
	}

	@ParameterizedTest
	@MethodSource(value = "invalidTaxRateSource")
	void shouldThrowException_whenInvalidTaxRate(double taxRate) {
		assertThrows(IllegalArgumentException.class, () -> new StandardTax(taxRate));
	}
}
