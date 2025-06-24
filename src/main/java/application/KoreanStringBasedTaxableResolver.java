package application;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.tax.factory.TaxableFactory;

public class KoreanStringBasedTaxableResolver implements TaxableResolver {

	private final Map<String, Function<TaxRate, Taxable>> registry;

	public KoreanStringBasedTaxableResolver(TaxableFactory taxableFactory) {
		this.registry = new HashMap<>();
		this.registry.put("일반과세", taxableFactory::createStandardTax);
		this.registry.put("비과세", taxRate -> taxableFactory.createNonTax());
		this.registry.put("세금우대", taxableFactory::createTaxBenefit);
	}

	@Override
	public Taxable resolve(String taxType, TaxRate taxRate) {
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
