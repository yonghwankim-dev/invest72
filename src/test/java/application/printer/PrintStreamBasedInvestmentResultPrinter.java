package application.printer;

import java.io.PrintStream;

public class PrintStreamBasedInvestmentResultPrinter implements InvestmentResultPrinter {

	private final PrintStream out;

	public PrintStreamBasedInvestmentResultPrinter(PrintStream out) {
		this.out = out;
	}

	@Override
	public void printTotalPrincipal(int amount) {
		out.println("total principal amount: " + formattedAmount(amount) + "원");
	}

	private String formattedAmount(int amount) {
		return String.format("%,d", amount);
	}

	@Override
	public void printInterest(int amount) {
		out.println("total interest amount: " + formattedAmount(amount) + "원");
	}
}
