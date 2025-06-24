package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.tax.FixedTaxRate;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class KoreanStringBasedTaxableResolverTest {

	private TaxableFactory taxableFactory;
	private TaxableResolver taxableResolver;

	@BeforeEach
	void setUp() {
		taxableFactory = new KoreanTaxableFactory();
		taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
	}

	@Test
	void created() {
		taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);

		assertNotNull(taxableResolver);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsStandardTax() {
		String taxType = "일반과세";
		TaxRate taxRate = new FixedTaxRate(0.154);

		Taxable taxable = taxableResolver.resolve(taxType, taxRate);

		assertInstanceOf(domain.tax.StandardTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsNonTax() {
		String taxType = "비과세";
		TaxRate taxRate = new FixedTaxRate(0.0);

		Taxable taxable = taxableResolver.resolve(taxType, taxRate);

		assertInstanceOf(domain.tax.NonTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsTaxBenefit() {
		String taxType = "세금우대";
		TaxRate taxRate = new FixedTaxRate(0.014);

		Taxable taxable = taxableResolver.resolve(taxType, taxRate);

		assertInstanceOf(domain.tax.TaxBenefit.class, taxable);
	}

	@Test
	void shouldThrowException_whenInvalidTaxType() {
		String taxType = "알수없는과세";
		TaxRate taxRate = new FixedTaxRate(0.1);

		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> taxableResolver.resolve(taxType, taxRate));

		assertEquals("Unknown tax type: 알수없는과세", exception.getMessage());
	}
}
