package application;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.List;

import adapter.console.reader.FixedDepositAmountReader;
import adapter.console.reader.InstallmentInvestmentAmountReader;
import adapter.console.reader.InvestmentAmountReader;
import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;
import domain.type.InvestmentType;

public class DefaultInvestmentAmountReaderRegistry implements InvestmentAmountReaderRegistry {

	private final PrintStream out;

	public DefaultInvestmentAmountReaderRegistry(PrintStream out) {
		this.out = out;
	}

	@Override
	public List<InvestmentAmountReader> getReaders() {
		// todo: intro field
		GuidePrinter printer = new WriterBasedGuidePrinter(new BufferedWriter(new OutputStreamWriter(out)));
		return List.of(
			new FixedDepositAmountReader(printer),
			new InstallmentInvestmentAmountReader(printer)
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
