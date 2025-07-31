package application.resolver;

import static co.invest72.investment.domain.tax.TaxType.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.tax.TaxType;

public class KoreanStringBasedTaxableResolver implements TaxableResolver {

	private final Map<TaxType, Function<TaxRate, Taxable>> registry;

	public KoreanStringBasedTaxableResolver(TaxableFactory taxableFactory) {
		this.registry = new EnumMap<>(TaxType.class);
		this.registry.put(STANDARD, taxableFactory::createStandardTax);
		this.registry.put(NON_TAX, taxRate -> taxableFactory.createNonTax());
		this.registry.put(TAX_BENEFIT, taxableFactory::createTaxBenefit);
	}

	@Override
	public Taxable resolve(TaxType taxType, TaxRate taxRate) {
		if (taxType == null || taxRate == null) {
			throw new IllegalArgumentException("Tax type and tax rate must not be null");
		}
		return registry.get(taxType).apply(taxRate);
	}
}
