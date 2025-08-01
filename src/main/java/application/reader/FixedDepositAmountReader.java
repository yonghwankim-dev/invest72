package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;

public class FixedDepositAmountReader implements InvestmentAmountReader {

	private final GuidePrinter printer;
	private final BufferedReader reader;

	public FixedDepositAmountReader(GuidePrinter printer, BufferedReader reader) {
		this.printer = printer;
		this.reader = reader;
	}

	@Override
	public String readAmount() throws IOException {
		printer.printFixedDepositAmountInputGuide();
		return reader.readLine();
	}
}
