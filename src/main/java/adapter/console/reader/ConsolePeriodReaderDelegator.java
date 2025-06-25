package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class ConsolePeriodReaderDelegator implements PeriodReaderDelegator {

	private final GuidePrinter guidePrinter;

	public ConsolePeriodReaderDelegator(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public int read(BufferedReader reader) throws IOException {
		guidePrinter.printPeriodInputGuide();
		return Integer.parseInt(reader.readLine());
	}
}
