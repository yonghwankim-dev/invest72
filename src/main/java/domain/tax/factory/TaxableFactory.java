package domain.tax.factory;

import domain.tax.NonTax;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.TaxType;

public interface TaxableFactory {
	Taxable createStandardTax(TaxRate taxRate);

	default Taxable createNonTax() {
		return new NonTax();
	}

	Taxable createTaxBenefit(TaxRate taxRate);

	Taxable createBy(TaxType taxType, TaxRate taxRate);
}
