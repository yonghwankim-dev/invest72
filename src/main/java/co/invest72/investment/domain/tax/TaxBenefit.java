package co.invest72.investment.domain.tax;

import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;

public class TaxBenefit implements Taxable {

	private final TaxRate taxRate;

	public TaxBenefit(TaxRate taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public int applyTax(int interest) {
		return taxRate.applyTo(interest);
	}
}
