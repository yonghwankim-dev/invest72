package application.reader.impl;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.InvestmentTypeReader;

public class InvestmentTypeInputReader implements InvestmentTypeReader {

	private final GuidePrinter guidePrinter;

	public InvestmentTypeInputReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String readInvestmentType(BufferedReader reader) throws IOException {
		guidePrinter.printInvestmentTypeInputGuide();
		return reader.readLine();
	}
}
