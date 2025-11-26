package co.invest72.investment.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxPercentFormatter implements TaxFormatter {

	@Override
	public String format(double value) {
		BigDecimal percent = BigDecimal.valueOf(value * 100)
			.setScale(2, RoundingMode.HALF_EVEN);
		String symbol = "%";
		return percent.stripTrailingZeros().toPlainString() + symbol;
	}
}
