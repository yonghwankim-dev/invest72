package co.invest72.investment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TaxPercentFormatterTest {

	@Test
	void canCreated() {
		TaxFormatter formatter = new TaxPercentFormatter();

		Assertions.assertThat(formatter).isNotNull();
	}

}
