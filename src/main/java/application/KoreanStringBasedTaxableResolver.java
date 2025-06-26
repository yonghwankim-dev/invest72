package application;

import static domain.type.TaxType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.tax.factory.TaxableFactory;
import domain.type.TaxType;

public class KoreanStringBasedTaxableResolver implements TaxableResolver {

	private final Map<TaxType, Function<TaxRate, Taxable>> registry;

	public KoreanStringBasedTaxableResolver(TaxableFactory taxableFactory) {
		this.registry = new HashMap<>();
		this.registry.put(STANDARD, taxableFactory::createStandardTax);
		this.registry.put(NON_TAX, taxRate -> taxableFactory.createNonTax());
		this.registry.put(TAX_BENEFIT, taxableFactory::createTaxBenefit);
	}

	@Override
	public Taxable resolve(TaxType taxType, TaxRate taxRate) {
		if (taxType == null || taxRate == null) {
			throw new IllegalArgumentException("Tax type and tax rate must not be null");
		}

		Function<TaxRate, Taxable> creator = registry.get(taxType);
		if (creator == null) {
			throw new IllegalArgumentException("Unknown tax type: " + taxType);
		}

		return creator.apply(taxRate);
	}
}
