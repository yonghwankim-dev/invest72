package application.reader.impl;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.InterestTypeReader;

public class InterestTypeInputReader implements InterestTypeReader {

	private final GuidePrinter guidePrinter;

	public InterestTypeInputReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String readInterestType(BufferedReader reader) throws IOException {
		guidePrinter.printInterestTypeInputGuide();
		return reader.readLine();
	}
}
