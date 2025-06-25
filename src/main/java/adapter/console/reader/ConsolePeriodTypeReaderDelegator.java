package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class ConsolePeriodTypeReaderDelegator implements PeriodTypeReaderDelegator {

	private final GuidePrinter guidePrinter;

	public ConsolePeriodTypeReaderDelegator(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String read(BufferedReader reader) throws IOException {
		guidePrinter.printPeriodTypeInputGuide();
		return reader.readLine();
	}
}
