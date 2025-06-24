package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class KoreanStringBasedTaxableResolverTest {

	@Test
	void created() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();

		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);

		assertNotNull(taxableResolver);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsStandardTax() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		String taxType = "일반과세";
		double taxPercentage = 15.4;

		Taxable taxable = taxableResolver.resolve(taxType, taxPercentage);

		assertInstanceOf(domain.tax.StandardTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsNonTax() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		String taxType = "비과세";
		double taxPercentage = 0.0;

		Taxable taxable = taxableResolver.resolve(taxType, taxPercentage);

		assertInstanceOf(domain.tax.NonTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsTaxBenefit() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		String taxType = "세금우대";
		double taxPercentage = 1.4;

		Taxable taxable = taxableResolver.resolve(taxType, taxPercentage);

		assertInstanceOf(domain.tax.TaxBenefit.class, taxable);
	}

	@Test
	void shouldThrowException_whenInvalidTaxType() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		String taxType = "알수없는과세";
		double taxPercentage = 10.0;

		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> taxableResolver.resolve(taxType, taxPercentage));

		assertEquals("Unknown tax type: 알수없는과세", exception.getMessage());
	}
}
