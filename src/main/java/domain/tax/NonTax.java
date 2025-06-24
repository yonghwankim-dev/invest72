package domain.tax;

public class NonTax implements Taxable {
	@Override
	public int applyTax(int interest) {
		return 0;
	}
}
