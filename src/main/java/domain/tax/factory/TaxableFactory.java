package domain.tax.factory;

import domain.tax.NonTax;
import domain.tax.TaxRate;
import domain.tax.Taxable;

public interface TaxableFactory {
	Taxable createStandardTax(TaxRate taxRate);

	default Taxable createNonTax() {
		return new NonTax();
	}

	Taxable createTaxBenefit(TaxRate taxRate);
}
