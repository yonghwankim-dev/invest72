package domain.tax.factory;

import domain.tax.FixedTaxRate;
import domain.tax.StandardTax;
import domain.tax.TaxBenefit;
import domain.tax.TaxRate;
import domain.tax.Taxable;

public class KoreanTaxableFactory implements TaxableFactory {
	@Override
	public Taxable createStandardTax(double taxRate) {
		// todo: refactor to use TaxRate interface
		TaxRate taxRatexx = new FixedTaxRate(taxRate);
		return new StandardTax(taxRatexx);
	}

	@Override
	public Taxable createTaxBenefit(double taxRate) {
		return new TaxBenefit(taxRate);
	}
}
