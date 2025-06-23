package tax.factory;

import tax.NonTax;
import tax.Taxable;

public interface TaxableFactory {
	Taxable createStandardTax();

	default Taxable createNonTax() {
		return new NonTax();
	}

	Taxable createTaxBenefit(double taxRate);
}
