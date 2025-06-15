public interface TaxableFactory {
	Taxable createStandardTax();

	default Taxable createNonTax() {
		return new NonTax();
	}
}
