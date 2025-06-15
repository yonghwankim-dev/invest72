public class TaxBenefit implements Taxable {

	private final double taxRate;

	public TaxBenefit(double taxRate) {
		this.taxRate = taxRate;
	}

	@Override
	public int applyTax(int preTaxInterest) {
		return (int) (preTaxInterest * taxRate);
	}
}
