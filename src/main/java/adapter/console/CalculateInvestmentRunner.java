package adapter.console;

import java.io.IOException;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.factory.UseCaseFactory;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import application.usecase.InvestmentUseCase;

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
		this.out = out;
		this.err = err;
		this.useCaseFactory = useCaseFactory;
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
			CalculateInvestmentResponse response = useCase.calInvestmentAmount(request);

			// 출력
			out.println("total principal amount: " + formattedAmount(response.getTotalPrincipalAmount()) + "원");
			out.println("total interest amount: " + formattedAmount(response.getInterest()) + "원");
			out.println("total investment amount: " + formattedAmount(response.getTotalProfitAmount()) + "원");

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}

	private static String formattedAmount(int amount) {
		return String.format("%,d", amount);
	}
}
