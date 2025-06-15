public class TaxBenefit implements Taxable {

	private final double taxRate;

	public TaxBenefit(double taxRate) {
		this.taxRate = taxRate;
		if (taxRate < 0 || taxRate >= 1) {
			throw new IllegalArgumentException("Tax rate must be between 0 and 1 (exclusive).");
		}
	}

	@Override
	public int applyTax(int preTaxInterest) {
		return (int) (preTaxInterest * taxRate);
	}
}
