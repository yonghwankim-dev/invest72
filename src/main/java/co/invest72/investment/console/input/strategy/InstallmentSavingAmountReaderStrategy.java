package co.invest72.investment.console.input.strategy;

import java.io.IOException;

import co.invest72.investment.console.input.reader.InvestmentAmountReader;
import co.invest72.investment.console.output.guide.GuidePrinter;

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
