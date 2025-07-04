package application.printer;

import java.io.PrintStream;

public class PrintStreamBasedInvestmentResultPrinter implements InvestmentResultPrinter {

	private final PrintStream out;

	public PrintStreamBasedInvestmentResultPrinter(PrintStream out) {
		this.out = out;
	}

	@Override
	public void printTotalPrincipal(int amount) {

	}
}
