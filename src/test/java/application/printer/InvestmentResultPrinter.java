package application.printer;

public interface InvestmentResultPrinter {
	void printTotalPrincipal(int amount);

	void printInterest(int amount);

	void printTax(int amount);
}
