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
			out.println("total principal amount: " + formattedAmount(response.getTotalPrincipalAmount()) + "원");
			out.println("total interest amount: " + formattedAmount(response.getInterest()) + "원");
			printTax(response.getTax());
			out.print("total profit amount: " + formattedAmount(response.getTotalProfitAmount()) + "원");

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}

	private String formattedAmount(int amount) {
		return String.format("%,d", amount);
	}

	private void printTax(int taxAmount) {
		String minus = "";
		if (taxAmount > 0) {
			minus = "-";
		}
		out.println("total tax amount: " + minus + formattedAmount(taxAmount) + "원");
	}
}
