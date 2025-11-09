package co.invest72.investment.console;

import java.io.IOException;
import java.io.PrintStream;

import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.console.input.delegator.CalculateExpirationInvestmentReaderDelegator;
import co.invest72.investment.console.output.InvestmentResultPrinter;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;

public class CalculateMonthlyInvestmentConsoleRunner {
	private final PrintStream err;
	private final CalculateExpirationInvestmentReaderDelegator delegator;
	private final InvestmentResultPrinter printer;
	private final CalculateMonthlyInvestment useCase;

	public CalculateMonthlyInvestmentConsoleRunner(PrintStream err,
		CalculateExpirationInvestmentReaderDelegator delegator, InvestmentResultPrinter printer,
		CalculateMonthlyInvestment useCase) {
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
			CalculateMonthlyInvestment.CalculateMonthlyInvestmentResponse response = useCase.calMonthlyInvestmentAmount(
				request);

			// 출력
			printer.printMonthlyInvestmentResults(response.monthlyInvestmentResults());

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
