package application;

import java.io.PrintStream;
import java.util.List;

import adapter.console.reader.FixedDepositAmountReader;
import adapter.console.reader.InstallmentInvestmentAmountReader;
import adapter.console.reader.InvestmentAmountReader;

public class DefaultInvestmentAmountReaderRegistry implements InvestmentAmountReaderRegistry {

	private final PrintStream out;

	public DefaultInvestmentAmountReaderRegistry(PrintStream out) {
		this.out = out;
	}

	@Override
	public List<InvestmentAmountReader> getReaders() {
		return List.of(
			new FixedDepositAmountReader(out),
			new InstallmentInvestmentAmountReader(out)
		);
	}
}
