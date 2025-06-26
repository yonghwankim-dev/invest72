package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class TaxTypeInputReader implements TaxTypeReader {

	private final GuidePrinter guidePrinter;

	public TaxTypeInputReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String read(BufferedReader reader) throws IOException {
		guidePrinter.printTaxTypeInputGuide();
		return reader.readLine();
	}
}
