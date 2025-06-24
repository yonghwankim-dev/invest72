package domain.tax;

public class StandardTax implements Taxable {

	private final TaxRate taxRate;

	public StandardTax(TaxRate taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public int applyTax(int interest) {
		return taxRate.applyTo(interest);
	}
}
