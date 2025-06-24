package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class KoreanStringBasedTaxableResolverTest {

	@Test
	void created() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();

		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);

		Assertions.assertNotNull(taxableResolver);
	}
}
