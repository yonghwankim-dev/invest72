package co.invest72.investment.console.output;

import java.util.List;

import co.invest72.investment.application.CalculateMonthlyInvestment;

public interface InvestmentResultPrinter {
	void printTotalPrincipal(int amount);

	void printInterest(int amount);

	void printTax(int amount);

	void printTotalProfit(int amount);

	void printMonthlyInvestmentResults(
		List<CalculateMonthlyInvestment.MonthlyInvestmentResult> monthlyInvestmentResults);
}
