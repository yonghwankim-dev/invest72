package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class ConsoleTaxRateReader implements TaxRateReader {

	private final GuidePrinter guidePrinter;

	public ConsoleTaxRateReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public double read(BufferedReader reader) throws IOException {
		guidePrinter.printTaxRateInputGuide();
		return toRate(Double.parseDouble(reader.readLine()));
	}

	private double toRate(double percent) {
		return percent / 100.0;
	}
}
