package application;

import domain.tax.TaxRate;
import domain.tax.Taxable;

public interface TaxableResolver {
	Taxable resolve(String taxType, TaxRate taxRate);
}
