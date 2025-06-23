public class KoreanTaxableFactory implements TaxableFactory {
	@Override
	public Taxable createStandardTax() {
		double taxRate = 0.154;
		return new StandardTax(taxRate);
	}

	@Override
	public Taxable createTaxBenefit(double taxRate) {
		return new
			TaxBenefit(taxRate);
	}
}
