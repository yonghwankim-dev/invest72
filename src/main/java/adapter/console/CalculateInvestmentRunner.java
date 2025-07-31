package adapter.console;

import java.io.IOException;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.factory.ExpirationInvestmentFactory;
import application.factory.InvestmentFactory;
import application.printer.InvestmentResultPrinter;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateExpirationInvestmentResponse;
import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.domain.Investment;

public class CalculateInvestmentRunner implements InvestmentApplicationRunner {
	private final PrintStream err;
	private final InvestmentReaderDelegator<CalculateInvestmentRequest> delegator;
	private final InvestmentResultPrinter printer;

	public CalculateInvestmentRunner(
		PrintStream err,
		InvestmentReaderDelegator<CalculateInvestmentRequest> delegator,
		InvestmentResultPrinter printer) {
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
			CalculateExpirationInvestment useCase = new CalculateExpirationInvestment(investmentFactory);

			// 계산 요청
			CalculateExpirationInvestmentResponse response = useCase.calInvestment(request);

			// 출력
			printer.printTotalPrincipal(response.totalPrincipalAmount());
			printer.printInterest(response.interest());
			printer.printTax(response.tax());
			printer.printTotalProfit(response.totalProfitAmount());

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
