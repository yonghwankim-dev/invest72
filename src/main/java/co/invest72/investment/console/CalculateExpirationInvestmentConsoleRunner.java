package co.invest72.investment.console;

import static co.invest72.investment.application.CalculateExpirationInvestment.*;

import java.io.IOException;
import java.io.PrintStream;

import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.console.input.delegator.CalculateExpirationInvestmentReaderDelegator;
import co.invest72.investment.console.output.InvestmentResultPrinter;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;

public class CalculateExpirationInvestmentConsoleRunner {
	private final PrintStream err;
	private final CalculateExpirationInvestmentReaderDelegator delegator;
	private final InvestmentResultPrinter printer;
	private final CalculateExpirationInvestment useCase;

	public CalculateExpirationInvestmentConsoleRunner(
		PrintStream err,
		CalculateExpirationInvestmentReaderDelegator delegator,
		InvestmentResultPrinter printer,
		CalculateExpirationInvestment useCase) {
		this.err = err;
		this.delegator = delegator;
		this.printer = printer;
		this.useCase = useCase;
	}

	public void run() {
		try {
			// 입력받기
			CalculateInvestmentRequest request = delegator.readRequest();

			// 계산 요청
			CalculateExpirationInvestmentResponse response = useCase.calInvestment(request);

			// 출력
			printer.printTotalPrincipal(response.totalPrincipal());
			printer.printInterest(response.totalInterest());
			printer.printTax(response.totalTax());
			printer.printTotalProfit(response.totalProfit());

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
