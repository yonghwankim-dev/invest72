package application;

import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.TaxType;

public interface TaxableResolver {
	Taxable resolve(TaxType taxType, TaxRate taxRate);
}
