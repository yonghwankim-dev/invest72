package co.invest72.investment.console;

import java.io.IOException;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.printer.InvestmentResultPrinter;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.application.dto.CalculateInvestmentRequest;
import co.invest72.investment.domain.investment.ExpirationInvestmentFactory;

public class ConsoleCalculateMonthlyInvestmentRunner implements InvestmentApplicationRunner {
	private final PrintStream err;
	private final InvestmentReaderDelegator<CalculateInvestmentRequest> delegator;
	private final InvestmentResultPrinter printer;

	public ConsoleCalculateMonthlyInvestmentRunner(PrintStream err,
		InvestmentReaderDelegator<CalculateInvestmentRequest> delegator, InvestmentResultPrinter printer) {
		this.err = err;
		this.delegator = delegator;
		this.printer = printer;
	}

	@Override
	public void run() {
		try {
			// 입력받기
			CalculateInvestmentRequest request = delegator.readInvestmentRequest();

			// UseCase 생성
			ExpirationInvestmentFactory investmentFactory = new ExpirationInvestmentFactory();
			CalculateMonthlyInvestment usecase = new CalculateMonthlyInvestment(investmentFactory);

			// 계산 요청
			CalculateMonthlyInvestment.CalculateMonthlyInvestmentResponse response = usecase.calMonthlyInvestmentAmount(
				request);

			// 출력
			printer.printMonthlyInvestmentResults(response.monthlyInvestmentResults());

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
