package domain.tax;

public class NonTax implements Taxable {
	@Override
	public int applyTax(int preTaxInterest) {
		return 0;
	}
}
