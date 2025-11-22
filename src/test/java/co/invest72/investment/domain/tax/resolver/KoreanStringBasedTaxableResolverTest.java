package co.invest72.investment.domain.tax.resolver;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.TaxableResolver;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.tax.NonTax;
import co.invest72.investment.domain.tax.StandardTax;
import co.invest72.investment.domain.tax.TaxBenefit;
import co.invest72.investment.domain.tax.TaxType;

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

		assertInstanceOf(StandardTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsNonTax() {
		taxType = TaxType.NON_TAX;
		taxRate = new FixedTaxRate(0.0);

		Taxable taxable = taxableResolver.resolve(taxType, taxRate);

		assertInstanceOf(NonTax.class, taxable);
	}

	@Test
	void shouldReturnTaxable_whenTaxTypeIsTaxBenefit() {
		taxType = TaxType.TAX_BENEFIT;
		taxRate = new FixedTaxRate(0.014);

		Taxable taxable = taxableResolver.resolve(taxType, taxRate);

		assertInstanceOf(TaxBenefit.class, taxable);
	}

	@Test
	void shouldThrowException_whenInvalidTaxType() {
		taxType = null;
		taxRate = new FixedTaxRate(0.1);

		Exception exception = assertThrows(IllegalArgumentException.class,
			() -> taxableResolver.resolve(taxType, taxRate));

		assertEquals("Tax type and totalTax rate must not be null", exception.getMessage());
	}
}
