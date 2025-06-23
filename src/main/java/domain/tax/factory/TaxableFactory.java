package domain.tax.factory;

import domain.tax.NonTax;
import domain.tax.Taxable;

public interface TaxableFactory {
	Taxable createStandardTax();

	default Taxable createNonTax() {
		return new NonTax();
	}

	Taxable createTaxBenefit(double taxRate);
}
