package application.strategy;

import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.InvestmentAmountReader;

public class InstallmentSavingAmountReaderStrategy implements InvestmentAmountReaderStrategy {

	private final GuidePrinter guidePrinter;

	public InstallmentSavingAmountReaderStrategy(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String readAmount(InvestmentAmountReader reader) throws IOException {
		guidePrinter.printInstallmentInvestmentInputGuide();
		return reader.readAmount();
	}
}
