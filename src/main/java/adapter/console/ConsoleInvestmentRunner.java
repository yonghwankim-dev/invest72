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
import application.TaxableResolver;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;
import domain.type.PeriodType;

public class ConsoleInvestmentRunner {
	private final InvestmentUseCase useCase;
	private final InputStream in;
	private final PrintStream out;
	private final InvestmentReaderDelegator delegator;
	private final TaxableResolver taxableResolver;

	public ConsoleInvestmentRunner(InvestmentUseCase useCase, InputStream in, PrintStream out,
		InvestmentReaderDelegator delegator, TaxableResolver taxableResolver) {
		this.useCase = useCase;
		this.in = in;
		this.out = out;
		this.delegator = delegator;
		this.taxableResolver = taxableResolver;
	}

	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			InvestmentType investmentType = delegator.readInvestmentType(reader);

			InvestmentAmount investmentAmount = delegator.readInvestmentAmount(investmentType, reader);

			PeriodType periodType = delegator.readPeriodType(reader);
			int period = delegator.readPeriod(reader);
			InvestPeriodFactory investPeriodFactory = new KoreanStringBasedInvestPeriodFactory();
			InvestPeriod investPeriod = investPeriodFactory.createBy(periodType, period);

			InterestType interestType = InterestType.from(delegator.readInterestType(reader));

			// todo: add factory
			InterestRate interestRate = delegator.readInterestRatePercent(reader);

			String taxType = delegator.readTaxType(reader);
			TaxRate taxRate = delegator.readTaxRate(reader);
			Taxable taxable = taxableResolver.resolve(taxType, taxRate);

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
