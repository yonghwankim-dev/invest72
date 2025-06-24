package domain.tax.factory;

import domain.tax.StandardTax;
import domain.tax.TaxBenefit;
import domain.tax.Taxable;

public class KoreanTaxableFactory implements TaxableFactory {
	@Override
	public Taxable createStandardTax(double taxRate) {
		return new StandardTax(taxRate);
	}

	@Override
	public Taxable createTaxBenefit(double taxRate) {
		return new
			TaxBenefit(taxRate);
	}
}
