package application.reader.impl;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.PeriodReader;

public class PeriodInputReader implements PeriodReader {

	private final GuidePrinter guidePrinter;

	public PeriodInputReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public int read(BufferedReader reader) throws IOException {
		guidePrinter.printPeriodInputGuide();
		return Integer.parseInt(reader.readLine());
	}
}
