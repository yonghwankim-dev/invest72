package application.strategy;

import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.InvestmentAmountReader;

public class FixedDepositAmountReaderStrategy implements InvestmentAmountReaderStrategy {

	private final GuidePrinter guidePrinter;

	public FixedDepositAmountReaderStrategy(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String readAmount(InvestmentAmountReader reader) throws IOException {
		guidePrinter.printFixedDepositAmountInputGuide();
		return reader.readAmount();
	}
}
