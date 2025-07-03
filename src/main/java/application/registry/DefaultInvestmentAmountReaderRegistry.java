package application.registry;

import java.util.List;

import adapter.ui.GuidePrinter;
import application.reader.InvestmentAmountReader;
import application.reader.impl.FixedDepositAmountReader;
import application.reader.impl.InstallmentInvestmentAmountReader;
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
