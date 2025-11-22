package co.invest72.investment.domain.tax;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

import co.invest72.investment.domain.TaxRate;

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
		return BigDecimal.valueOf(rate)
			.multiply(BigDecimal.valueOf(amount), MathContext.DECIMAL64)
			.setScale(0, RoundingMode.HALF_EVEN)
			.intValueExact();
	}

	@Override
	public BigDecimal applyTo(BigDecimal amount) {
		return BigDecimal.valueOf(rate)
			.multiply(amount, MathContext.DECIMAL64);
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
