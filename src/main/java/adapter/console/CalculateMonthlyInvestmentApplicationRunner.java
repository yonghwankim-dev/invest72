package adapter.console;

import java.io.IOException;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.factory.UseCaseFactory;
import application.printer.InvestmentResultPrinter;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateMonthlyInvestmentResponse;
import application.usecase.InvestmentUseCase;

public class CalculateMonthlyInvestmentApplicationRunner implements InvestmentApplicationRunner {
	private final UseCaseFactory useCaseFactory;
	private final PrintStream err;
	private final InvestmentReaderDelegator<CalculateInvestmentRequest> delegator;
	private final InvestmentResultPrinter printer;

	public CalculateMonthlyInvestmentApplicationRunner(UseCaseFactory useCaseFactory, PrintStream err,
		InvestmentReaderDelegator<CalculateInvestmentRequest> delegator, InvestmentResultPrinter printer) {
		this.useCaseFactory = useCaseFactory;
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
			InvestmentUseCase useCase = useCaseFactory.createCalculateInvestmentUseCase();

			// 계산 요청
			CalculateMonthlyInvestmentResponse response = useCase.calMonthlyInvestmentAmount(request);

			// 출력
			printer.printMonthlyInvestmentResults(response.getMonthlyInvestmentResults());

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
