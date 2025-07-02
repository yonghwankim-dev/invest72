package adapter.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import adapter.console.reader.InvestmentReaderDelegator;
import application.InvestmentRequest;
import application.InvestmentUseCase;
import application.TaxableResolver;
import application.UseCaseFactory;

public class CalculateInvestmentRunner implements InvestmentApplicationRunner {
	private final UseCaseFactory useCaseFactory;
	private final InputStream in;
	private final PrintStream out;
	private final PrintStream err;
	private final InvestmentReaderDelegator delegator;
	private final TaxableResolver taxableResolver;

	public CalculateInvestmentRunner(UseCaseFactory useCaseFactory, InputStream in,
		PrintStream out, PrintStream err,
		InvestmentReaderDelegator delegator, TaxableResolver taxableResolver) {
		this.useCaseFactory = useCaseFactory;
		this.in = in;
		this.out = out;
		this.err = err;
		this.delegator = delegator;
		this.taxableResolver = taxableResolver;
	}

	@Override
	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			// 입력받기
			InvestmentRequest request = delegator.readInvestmentRequest(reader, taxableResolver);

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
