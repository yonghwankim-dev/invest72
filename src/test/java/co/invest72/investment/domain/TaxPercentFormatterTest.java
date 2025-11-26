package co.invest72.investment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TaxPercentFormatterTest {

	@Test
	void canCreated() {
		TaxFormatter formatter = new TaxPercentFormatter();

		Assertions.assertThat(formatter).isNotNull();
	}

	@Test
	void format() {
		TaxFormatter formatter = new TaxPercentFormatter();

		String formatted = formatter.format(0.154);

		Assertions.assertThat(formatted).isEqualTo("15.4%");
	}

}
