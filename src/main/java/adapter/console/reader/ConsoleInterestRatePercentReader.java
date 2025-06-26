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
	public int read(BufferedReader reader) throws IOException {
		guidePrinter.printInterestRatePercentInputGuide();
		return Integer.parseInt(reader.readLine());
	}
}
