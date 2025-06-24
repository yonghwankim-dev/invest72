package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class KoreanStringBasedTaxableResolverTest {

	@Test
	void created() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();

		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);

		Assertions.assertNotNull(taxableResolver);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsStandardTax() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		String taxType = "일반과세";
		double taxPercentage = 15.4;

		Taxable taxable = taxableResolver.resolve(taxType, taxPercentage);

		Assertions.assertInstanceOf(domain.tax.StandardTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsNonTax() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		String taxType = "비과세";
		double taxPercentage = 0.0;

		Taxable taxable = taxableResolver.resolve(taxType, taxPercentage);

		Assertions.assertInstanceOf(domain.tax.NonTax.class, taxable);
	}
}
