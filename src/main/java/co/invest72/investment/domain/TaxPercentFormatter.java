package co.invest72.investment.domain;

public class TaxPercentFormatter implements TaxFormatter {

	@Override
	public String format(double value) {
		double percent = value * 100;
		return String.format("%.1f%%", percent);
	}
}
