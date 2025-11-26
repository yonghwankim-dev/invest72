package co.invest72.investment.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TaxFormatterTest {

	@Test
	void canCreated() {
		TaxFormatter formatter = new TaxFormatter();

		Assertions.assertThat(formatter).isNotNull();
	}
}
