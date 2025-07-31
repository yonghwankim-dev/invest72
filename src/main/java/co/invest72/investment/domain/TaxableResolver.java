package co.invest72.investment.domain;

import co.invest72.investment.domain.tax.TaxType;

public interface TaxableResolver {
	Taxable resolve(TaxType taxType, TaxRate taxRate);
}
