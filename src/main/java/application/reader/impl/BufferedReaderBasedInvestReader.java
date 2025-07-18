package application.reader.impl;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.InvestReader;

public class BufferedReaderBasedInvestReader implements InvestReader {

	private final BufferedReader reader;
	private final GuidePrinter guidePrinter;

	public BufferedReaderBasedInvestReader(BufferedReader reader, GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
		this.reader = reader;
	}

	@Override
	public String readInvestmentType() throws IOException {
		guidePrinter.printInvestmentTypeInputGuide();
		return readLine();
	}

	private String readLine() throws IOException {
		return reader.readLine();
	}

	@Override
	public String readFixedDepositAmount() throws IOException {
		guidePrinter.printFixedDepositAmountInputGuide();
		return readLine();
	}

	@Override
	public String readInstallmentSavingAmount() throws IOException {
		guidePrinter.printInstallmentInvestmentInputGuide();
		return readLine();
	}
}
