package co.invest72.investment.domain;

import co.invest72.investment.domain.tax.NonTax;
import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;

public interface TaxableFactory {
	Taxable createStandardTax(TaxRate taxRate);

	default Taxable createNonTax() {
		return new NonTax();
	}

	Taxable createTaxBenefit(TaxRate taxRate);
}
