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
		double taxRate = taxPercentage / 100.0;
		if (taxType.equals("일반과세")) {
			return taxableFactory.createStandardTax(taxRate);
		} else if (taxType.equals("비과세")) {
			return taxableFactory.createNonTax();
		} else if (taxType.equals("세금우대")) {
			return taxableFactory.createTaxBenefit(taxRate);
		}
		throw new IllegalArgumentException("Unknown tax type: " + taxType);
	}
}
