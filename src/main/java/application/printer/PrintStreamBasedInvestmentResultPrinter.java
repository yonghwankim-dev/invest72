package application.printer;

import java.io.PrintStream;

public class PrintStreamBasedInvestmentResultPrinter implements InvestmentResultPrinter {

	private final PrintStream out;

	public PrintStreamBasedInvestmentResultPrinter(PrintStream out) {
		this.out = out;
	}

	@Override
	public void printTotalPrincipal(int amount) {
		out.println("원금 합계: " + formattedAmount(amount) + "원");
	}

	private String formattedAmount(int amount) {
		return String.format("%,d", amount);
	}

	@Override
	public void printInterest(int amount) {
		out.println("세전 이자: " + formattedAmount(amount) + "원");
	}

	@Override
	public void printTax(int amount) {
		if (amount > 0) {
			amount = -amount; // Tax is shown as a negative value
		}
		String formatted = String.format("이자 과세: %s원", formattedAmount(amount));
		out.println(formatted);
	}

	@Override
	public void printTotalProfit(int amount) {
		out.println("세후 수령액: " + formattedAmount(amount) + "원");
	}
}
