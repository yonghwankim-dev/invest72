package domain.tax.factory;

import domain.tax.StandardTax;
import domain.tax.TaxBenefit;
import domain.tax.TaxRate;
import domain.tax.Taxable;

public class KoreanTaxableFactory implements TaxableFactory {
	@Override
	public Taxable createStandardTax(TaxRate taxRate) {
		return new StandardTax(taxRate);
	}

	@Override
	public Taxable createTaxBenefit(TaxRate taxRate) {
		return new TaxBenefit(taxRate);
	}
}
