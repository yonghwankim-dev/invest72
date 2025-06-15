public class StandardTax implements Taxable {

	private final double taxRate;

	public StandardTax(double taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public int calculateInterestTaxation(int preTaxInterest) {
		return (int)(preTaxInterest * taxRate);
	}
}
