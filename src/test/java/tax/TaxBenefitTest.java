package tax;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import tax.TaxBenefit;
import tax.Taxable;

class TaxBenefitTest {

	public static Stream<Arguments> invalidTaxRateSource() {
		return Stream.of(
			Arguments.of(-0.01),
			Arguments.of(1.0)
		);
	}

	@Test
	void created(){
		Taxable taxable = new TaxBenefit(0.014);
		assertNotNull(taxable);
	}

	@ParameterizedTest
	@MethodSource(value = "invalidTaxRateSource")
	void shouldThrowException_whenInvalidTaxRate(double taxRate){
		assertThrows(IllegalArgumentException.class, () -> new TaxBenefit(taxRate));
	}

}
