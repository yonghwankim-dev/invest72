package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;
import domain.type.InvestmentType;

public class ConsoleInvestmentTypeReaderDelegator implements InvestmentTypeReaderDelegator {

	private final GuidePrinter guidePrinter;

	public ConsoleInvestmentTypeReaderDelegator(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public InvestmentType read(BufferedReader reader) throws IOException {
		guidePrinter.printInvestmentTypeInputGuide();
		String type = reader.readLine();
		return InvestmentType.from(type);
	}
}
