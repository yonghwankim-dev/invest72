package adapter.console;

import java.io.IOException;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.factory.ExpirationInvestmentFactory;
import application.factory.InvestmentFactory;
import application.printer.InvestmentResultPrinter;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateMonthlyInvestmentResponse;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.domain.Investment;

public class CalculateMonthlyInvestmentApplicationRunner implements InvestmentApplicationRunner {
	private final PrintStream err;
	private final InvestmentReaderDelegator<CalculateInvestmentRequest> delegator;
	private final InvestmentResultPrinter printer;

	public CalculateMonthlyInvestmentApplicationRunner(PrintStream err,
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
			InvestmentFactory<Investment> investmentFactory = new ExpirationInvestmentFactory();
			CalculateMonthlyInvestment usecase = new CalculateMonthlyInvestment(investmentFactory);

			// 계산 요청
			CalculateMonthlyInvestmentResponse response = usecase.calMonthlyInvestmentAmount(request);

			// 출력
			printer.printMonthlyInvestmentResults(response.getMonthlyInvestmentResults());

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
