package application.reader.impl;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.TaxTypeReader;

public class TaxTypeInputReader implements TaxTypeReader {

	private final GuidePrinter guidePrinter;

	public TaxTypeInputReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String readTaxType(BufferedReader reader) throws IOException {
		guidePrinter.printTaxTypeInputGuide();
		return reader.readLine();
	}
}
