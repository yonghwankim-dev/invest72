package application;

import java.util.List;

import adapter.console.reader.FixedDepositAmountReader;
import adapter.console.reader.InstallmentInvestmentAmountReader;
import adapter.console.reader.InvestmentAmountReader;
import adapter.console.writer.GuidePrinter;
import domain.type.InvestmentType;

public class DefaultInvestmentAmountReaderRegistry implements InvestmentAmountReaderRegistry {

	private final GuidePrinter guidePrinter;

	public DefaultInvestmentAmountReaderRegistry(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public List<InvestmentAmountReader> getReaders() {
		return List.of(
			new FixedDepositAmountReader(this.guidePrinter),
			new InstallmentInvestmentAmountReader(this.guidePrinter)
		);
	}

	@Override
	public InvestmentAmountReader getReaderBy(InvestmentType investmentType) {
		for (InvestmentAmountReader reader : getReaders()) {
			if (reader.supports(investmentType)) {
				return reader;
			}
		}
		throw new IllegalArgumentException("not supported investment type: " + investmentType);
	}
}
