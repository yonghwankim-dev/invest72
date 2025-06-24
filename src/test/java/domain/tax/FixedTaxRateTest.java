package domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixedTaxRateTest {

	private TaxRate fixedTaxRate;

	@BeforeEach
	void setUp() {
		fixedTaxRate = new FixedTaxRate(0.154);
	}

	@Test
	void created() {
		assertNotNull(fixedTaxRate);
	}

	@Test
	void shouldReturnTaxAmount_whenApplyToAmount() {
		int amount = 1000;

		int taxAmount = fixedTaxRate.applyTo(amount);

		int expectedTaxAmount = 154;
		assertEquals(expectedTaxAmount, taxAmount);
	}
}
