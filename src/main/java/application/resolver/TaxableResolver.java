package application.resolver;

import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.tax.TaxType;

public interface TaxableResolver {
	Taxable resolve(TaxType taxType, TaxRate taxRate);
}
