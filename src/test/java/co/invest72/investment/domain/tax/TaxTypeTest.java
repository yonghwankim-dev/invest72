package co.invest72.investment.domain.tax;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaxTypeTest {

	@Test
	void shouldReturnTaxType_whenInputIsNoneTax() {
		TaxType taxType = TaxType.from("비과세");
		assertEquals(TaxType.NON_TAX, taxType);
	}
}
