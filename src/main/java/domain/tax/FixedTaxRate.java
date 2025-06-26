package domain.tax;

import java.util.Objects;

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

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		FixedTaxRate that = (FixedTaxRate)object;
		return Double.compare(rate, that.rate) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rate);
	}
}
