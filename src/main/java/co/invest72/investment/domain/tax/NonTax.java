package co.invest72.investment.domain.tax;

import co.invest72.investment.domain.Taxable;

public class NonTax implements Taxable {
	@Override
	public int applyTax(int interest) {
		return 0;
	}
}
