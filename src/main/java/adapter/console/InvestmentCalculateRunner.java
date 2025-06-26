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
import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class InvestmentCalculateRunner implements InvestmentApplicationRunner {
	private final InvestmentUseCase useCase;
	private final InputStream in;
	private final PrintStream out;
	private final PrintStream err;
	private final InvestmentReaderDelegator delegator;
	private final TaxableResolver taxableResolver;

	public InvestmentCalculateRunner(InvestmentUseCase useCase, InputStream in, PrintStream out, PrintStream err,
		InvestmentReaderDelegator delegator, TaxableResolver taxableResolver) {
		this.useCase = useCase;
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
			InvestmentType investmentType = delegator.readInvestmentType(reader);
			InvestmentAmount investmentAmount = delegator.readInvestmentAmount(investmentType, reader);
			InvestPeriod investPeriod = delegator.readInvestPeriod(reader);
			InterestType interestType = delegator.readInterestType(reader);
			InterestRate interestRate = delegator.readInterestRatePercent(reader);
			Taxable taxable = delegator.readTaxable(reader, taxableResolver);

			// Request 생성
			InvestmentRequest request = new InvestmentRequest(
				investmentType,
				investmentAmount,
				investPeriod,
				interestType,
				interestRate,
				taxable
			);

			// 계산 요청
			int result = useCase.calAmount(request);

			// 출력
			out.println("total investment amount: " + result + "원");

		} catch (IOException | IllegalArgumentException e) {
			err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}
}
