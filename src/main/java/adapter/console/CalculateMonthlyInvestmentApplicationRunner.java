package adapter.console;

import java.io.IOException;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import application.delegator.InvestmentReaderDelegator;
import application.printer.InvestmentResultPrinter;
import application.request.CalculateInvestmentRequest;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.investment.ExpirationInvestmentFactory;
import co.invest72.investment.domain.investment.InvestmentFactory;

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
			CalculateMonthlyInvestment.CalculateMonthlyInvestmentResponse response = usecase.calMonthlyInvestmentAmount(
				request);

			// 출력
			printer.printMonthlyInvestmentResults(response.monthlyInvestmentResults());

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
