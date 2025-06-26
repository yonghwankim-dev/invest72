package adapter.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import adapter.console.reader.InvestmentReaderDelegator;
import application.InvestPeriodFactory;
import application.InvestmentRequest;
import application.InvestmentUseCase;
import application.KoreanStringBasedInvestPeriodFactory;
import application.KoreanStringBasedTaxableResolver;
import application.TaxableResolver;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InterestType;
import domain.type.InvestmentType;
import domain.type.PeriodType;

public class ConsoleInvestmentRunner {
	private final InvestmentUseCase useCase;
	private final InputStream in;
	private final PrintStream out;
	private final InvestmentReaderDelegator delegator;

	public ConsoleInvestmentRunner(InvestmentUseCase useCase, InputStream in, PrintStream out,
		InvestmentReaderDelegator delegator) {
		this.useCase = useCase;
		this.in = in;
		this.out = out;
		this.delegator = delegator;
	}

	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			InvestmentType investmentType = delegator.readInvestmentType(reader);

			InvestmentAmount investmentAmount = delegator.readInvestmentAmount(
				investmentType,
				reader
			);

			PeriodType periodType = delegator.readPeriodType(reader);

			int period = delegator.readPeriod(reader);

			// todo: extract to delegator
			out.print("이자 방식을 입력하세요 (단리 or 복리): ");
			String interestTypeText = reader.readLine();
			interestTypeText = delegator.readInterestType(reader);

			out.print("이자율을 입력하세요 (%): ");
			int interestRatePercent = Integer.parseInt(reader.readLine());

			out.print("과세 유형을 입력하세요 (일반과세, 비과세, 세금우대): ");
			String taxType = reader.readLine();

			out.print("세율을 입력하세요 (세금우대형일 경우 %, 아니면 0): ");
			double taxRate = toRate(Double.parseDouble(reader.readLine()));

			InvestPeriodFactory investPeriodFactory = new KoreanStringBasedInvestPeriodFactory();
			InvestPeriod investPeriod = investPeriodFactory.createBy(periodType, period);

			TaxableFactory taxableFactory = new KoreanTaxableFactory();
			TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
			Taxable taxable = taxableResolver.resolve(taxType, new FixedTaxRate(taxRate));

			InterestType interestType = InterestType.from(interestTypeText);
			// todo: add factory
			InterestRate interestRate = new AnnualInterestRate(toRate(interestRatePercent));

			InvestmentRequest request = new InvestmentRequest(
				investmentType,
				investmentAmount,
				investPeriod,
				interestType,
				interestRate,
				taxable
			);

			int result = useCase.calAmount(request);
			out.println("total investment amount: " + result + "원");

		} catch (IOException | IllegalArgumentException e) {
			System.err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}

	private double toRate(double value) {
		return value / 100.0;
	}
}
