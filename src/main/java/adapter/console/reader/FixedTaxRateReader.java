package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;
import domain.tax.FixedTaxRate;
import domain.tax.TaxRate;

public class FixedTaxRateReader implements TaxRateReader {

	private final GuidePrinter guidePrinter;

	public FixedTaxRateReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public TaxRate read(BufferedReader reader) throws IOException {
		guidePrinter.printTaxRateInputGuide();
		return new FixedTaxRate(toRate(Double.parseDouble(reader.readLine())));
	}

	private double toRate(double percent) {
		return percent / 100.0;
	}
}
