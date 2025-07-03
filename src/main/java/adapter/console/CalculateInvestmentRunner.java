package adapter.console;

import java.io.IOException;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import adapter.console.reader.InvestmentReaderDelegator;
import application.CalculateInvestmentRequest;
import application.InvestmentUseCase;
import application.UseCaseFactory;

public class CalculateInvestmentRunner implements InvestmentApplicationRunner {
	private final UseCaseFactory useCaseFactory;
	private final PrintStream out;
	private final PrintStream err;
	private final InvestmentReaderDelegator delegator;

	public CalculateInvestmentRunner(
		PrintStream out,
		PrintStream err,
		UseCaseFactory useCaseFactory,
		InvestmentReaderDelegator delegator) {
		this.useCaseFactory = useCaseFactory;
		this.out = out;
		this.err = err;
		this.delegator = delegator;
	}

	@Override
	public void run() {
		try {
			// 입력받기
			CalculateInvestmentRequest request = delegator.readInvestmentRequest();

			// UseCase 생성
			InvestmentUseCase useCase = useCaseFactory.createCalculateInvestmentUseCase();

			// 계산 요청
			int result = useCase.calAmount(request);

			// 출력
			out.println("total investment amount: " + result + "원");

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
