package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;

public class ConsoleInterestTypeReader implements InterestTypeReader {

	private final GuidePrinter guidePrinter;

	public ConsoleInterestTypeReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String read(BufferedReader reader) throws IOException {
		guidePrinter.printInterestTypeInputGuide();
		return reader.readLine();
	}
}
