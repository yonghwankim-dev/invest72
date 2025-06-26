package adapter.console.reader;

import java.io.BufferedReader;

import adapter.console.writer.GuidePrinter;

public class ConsoleInterestTypeReader implements InterestTypeReader {

	private final GuidePrinter guidePrinter;

	public ConsoleInterestTypeReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String read(BufferedReader reader) throws Exception {
		guidePrinter.printInterestTypeInputGuide();
		return reader.readLine();
	}
}
