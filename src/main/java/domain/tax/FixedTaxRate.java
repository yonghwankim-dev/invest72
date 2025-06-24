package domain.tax;

public class FixedTaxRate implements TaxRate {

	private final double rate;

	public FixedTaxRate(double rate) {
		this.rate = rate;
		if (this.rate < 0 || this.rate >= 1) {
			throw new IllegalArgumentException("Tax rate must be between 0 and 1 (exclusive).");
		}
	}

	@Override
	public int applyTo(int amount) {
		return (int)Math.round(amount * rate);
	}

	@Override
	public double getRate() {
		return this.rate;
	}
}
