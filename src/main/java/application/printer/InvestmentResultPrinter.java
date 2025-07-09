package application.printer;

import java.util.List;

import application.response.MonthlyInvestmentResult;

public interface InvestmentResultPrinter {
	void printTotalPrincipal(int amount);

	void printInterest(int amount);

	void printTax(int amount);

	void printTotalProfit(int amount);

	void printMonthlyInvestmentResults(List<MonthlyInvestmentResult> monthlyInvestmentResults);
}
