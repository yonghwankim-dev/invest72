package domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixedTaxRateTest {

	@Test
	void created() {
		TaxRate fixedTaxRate = new FixedTaxRate(0.15);

		assertNotNull(fixedTaxRate);
	}

}
