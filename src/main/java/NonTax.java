public class NonTax implements Taxable {
	@Override
	public int calculateInterestTaxation(int preTaxInterest) {
		return 0;
	}
}
