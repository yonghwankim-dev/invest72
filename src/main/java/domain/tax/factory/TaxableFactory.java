package domain.tax.factory;

import domain.tax.NonTax;
import domain.tax.Taxable;

public interface TaxableFactory {
	Taxable createStandardTax(double taxRate);

	default Taxable createNonTax() {
		return new NonTax();
	}

	Taxable createTaxBenefit(double taxRate);
}
