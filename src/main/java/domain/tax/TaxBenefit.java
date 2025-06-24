package domain.tax;

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
