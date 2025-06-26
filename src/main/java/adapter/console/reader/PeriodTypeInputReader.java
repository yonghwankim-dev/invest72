package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class PeriodTypeInputReader implements PeriodTypeReader {

	private final GuidePrinter guidePrinter;

	public PeriodTypeInputReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String read(BufferedReader reader) throws IOException {
		guidePrinter.printPeriodTypeInputGuide();
		return reader.readLine();
	}
}
