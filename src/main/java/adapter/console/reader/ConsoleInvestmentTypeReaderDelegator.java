package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class ConsoleInvestmentTypeReaderDelegator implements InvestmentTypeReaderDelegator {

	private final GuidePrinter guidePrinter;

	public ConsoleInvestmentTypeReaderDelegator(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String read(BufferedReader reader) throws IOException {
		guidePrinter.printInvestmentTypeInputGuide();
		return reader.readLine();
	}
}
