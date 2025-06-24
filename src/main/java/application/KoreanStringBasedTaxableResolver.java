package application;

import domain.tax.Taxable;
import domain.tax.factory.TaxableFactory;

public class KoreanStringBasedTaxableResolver implements TaxableResolver {

	private final TaxableFactory taxableFactory;

	public KoreanStringBasedTaxableResolver(TaxableFactory taxableFactory) {
		this.taxableFactory = taxableFactory;
	}

	@Override
	public Taxable resolve(String taxType, double taxPercentage) {
		return null;
	}
}
