package adapter.console;

import java.io.IOException;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.factory.UseCaseFactory;
import application.printer.InvestmentResultPrinter;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import application.usecase.InvestmentUseCase;

public class CalculateInvestmentRunner implements InvestmentApplicationRunner {
	private final UseCaseFactory useCaseFactory;
	private final PrintStream out;
	private final PrintStream err;
	private final InvestmentReaderDelegator delegator;
	private final InvestmentResultPrinter printer;

	public CalculateInvestmentRunner(
		PrintStream out,
		PrintStream err,
		UseCaseFactory useCaseFactory,
		InvestmentReaderDelegator delegator,
		InvestmentResultPrinter printer) {
		this.out = out;
		this.err = err;
		this.useCaseFactory = useCaseFactory;
		this.delegator = delegator;
		this.printer = printer;
	}

	@Override
	public void run() {
		try {
			// 입력받기
			CalculateInvestmentRequest request = delegator.readInvestmentRequest();

			// UseCase 생성
			InvestmentUseCase useCase = useCaseFactory.createCalculateInvestmentUseCase();

			// 계산 요청
			CalculateInvestmentResponse response = useCase.calInvestmentAmount(request);

			// 출력
			printer.printTotalPrincipal(response.getTotalPrincipalAmount());
			printer.printInterest(response.getInterest());
			printer.printTax(response.getTax());
			printer.printTotalProfit(response.getTotalProfitAmount());

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
