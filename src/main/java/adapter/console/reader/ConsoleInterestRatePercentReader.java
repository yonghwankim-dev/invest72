package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class ConsoleInterestRatePercentReader implements InterestRatePercentReader {

	private final GuidePrinter guidePrinter;

	public ConsoleInterestRatePercentReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public double read(BufferedReader reader) throws IOException {
		guidePrinter.printInterestRatePercentInputGuide();
		int percent = Integer.parseInt(reader.readLine());
		return toRate(percent);
	}

	private double toRate(int value) {
		return value / 100.0;
	}
}
