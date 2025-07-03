package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import domain.tax.FixedTaxRate;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.TaxType;

class KoreanStringBasedTaxableResolverTest {

	private TaxableFactory taxableFactory;
	private TaxableResolver taxableResolver;
	private TaxType taxType;
	private TaxRate taxRate;

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
		taxType = TaxType.STANDARD;
		taxRate = new FixedTaxRate(0.154);

		Taxable taxable = taxableResolver.resolve(taxType, taxRate);

		assertInstanceOf(domain.tax.StandardTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsNonTax() {
		taxType = TaxType.NON_TAX;
		taxRate = new FixedTaxRate(0.0);

		Taxable taxable = taxableResolver.resolve(taxType, taxRate);

		assertInstanceOf(domain.tax.NonTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsTaxBenefit() {
		taxType = TaxType.TAX_BENEFIT;
		taxRate = new FixedTaxRate(0.014);

		Taxable taxable = taxableResolver.resolve(taxType, taxRate);

		assertInstanceOf(domain.tax.TaxBenefit.class, taxable);
	}

	@Test
	void shouldThrowException_whenInvalidTaxType() {
		taxType = null;
		taxRate = new FixedTaxRate(0.1);

		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> taxableResolver.resolve(taxType, taxRate));

		assertEquals("Tax type and tax rate must not be null", exception.getMessage());
	}
}
