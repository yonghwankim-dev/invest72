package co.invest72.investment.domain.tax;

import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.tax.StandardTax;
import co.invest72.investment.domain.tax.TaxBenefit;
import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;

public class KoreanTaxableFactory implements TaxableFactory {
	@Override
	public Taxable createStandardTax(TaxRate taxRate) {
		return new StandardTax(taxRate);
	}

	@Override
	public Taxable createTaxBenefit(TaxRate taxRate) {
		return new TaxBenefit(taxRate);
	}
}
