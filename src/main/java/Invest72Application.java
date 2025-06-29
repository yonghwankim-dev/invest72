import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import adapter.InvestmentApplicationRunner;
import adapter.console.InvestmentCalculateRunner;
import adapter.console.reader.AnnualInterestRateReader;
import adapter.console.reader.ConsoleInterestTypeReader;
import adapter.console.reader.DefaultInvestmentReaderDelegator;
import adapter.console.reader.FixedTaxRateReader;
import adapter.console.reader.InterestRatePercentReader;
import adapter.console.reader.InterestTypeReader;
import adapter.console.reader.InvestmentAmountReaderDelegator;
import adapter.console.reader.InvestmentReaderDelegator;
import adapter.console.reader.InvestmentTypeInputReader;
import adapter.console.reader.InvestmentTypeReader;
import adapter.console.reader.PeriodInputReader;
import adapter.console.reader.PeriodReader;
import adapter.console.reader.PeriodTypeInputReader;
import adapter.console.reader.PeriodTypeReader;
import adapter.console.reader.RegistryBasedInvestmentAmountDelegator;
import adapter.console.reader.TaxRateReader;
import adapter.console.reader.TaxTypeInputReader;
import adapter.console.reader.TaxTypeReader;
import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;
import application.CalculateInvestmentUseCase;
import application.DefaultInvestmentAmountReaderRegistry;
import application.DefaultInvestmentFactory;
import application.InvestmentAmountReaderRegistry;
import application.InvestmentFactory;
import application.InvestmentUseCase;
import application.KoreanStringBasedTaxableResolver;
import application.TaxableResolver;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

public class Invest72Application {
	public static void main(String[] args) {
		InvestmentApplicationRunner runner = createConsoleBasedInvestmentCalculateRunner();
		runner.run();
	}

	private static InvestmentApplicationRunner createConsoleBasedInvestmentCalculateRunner() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		InputStream in = System.in;
		PrintStream out = System.out;
		PrintStream err = System.err;

		GuidePrinter guidPrinter = createGuidPrinter(out);
		InvestmentAmountReaderRegistry investmentAmountReaderRegistry = createInvestmentAmountReaderRegistry(
			guidPrinter);
		InvestmentTypeReader investmentTypeReaderDelegator = createInvestTypeReaderDelegator(
			guidPrinter);
		InvestmentReaderDelegator investmentReaderDelegator = createInvestmentReaderDelegator(
			investmentAmountReaderRegistry, guidPrinter, investmentTypeReaderDelegator);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		return new InvestmentCalculateRunner(
			useCase,
			in,
			out,
			err,
			investmentReaderDelegator,
			taxableResolver);
	}

	private static InvestmentReaderDelegator createInvestmentReaderDelegator(
		InvestmentAmountReaderRegistry investmentAmountReaderRegistry, GuidePrinter guidPrinter,
		InvestmentTypeReader investmentTypeReaderDelegator) {
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = createInvestmentAmountReaderDelegator(
			investmentAmountReaderRegistry);
		PeriodTypeReader periodTypeReader = createPeriodTypeReaderDelegator(guidPrinter);
		PeriodReader periodReader = new PeriodInputReader(guidPrinter);
		InterestTypeReader interestTypeReader = new ConsoleInterestTypeReader(guidPrinter);
		InterestRatePercentReader interestRatePercentReader = new AnnualInterestRateReader(guidPrinter);
		TaxTypeReader taxTypeReader = new TaxTypeInputReader(guidPrinter);
		TaxRateReader taxRateReader = new FixedTaxRateReader(guidPrinter);
		return new DefaultInvestmentReaderDelegator(
			investmentTypeReaderDelegator,
			investmentAmountReaderDelegator,
			periodTypeReader,
			periodReader,
			interestTypeReader,
			interestRatePercentReader,
			taxTypeReader,
			taxRateReader
		);
	}

	private static GuidePrinter createGuidPrinter(PrintStream out) {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		return new WriterBasedGuidePrinter(bufferedWriter);
	}

	private static InvestmentAmountReaderRegistry createInvestmentAmountReaderRegistry(GuidePrinter guidePrinter) {
		return new DefaultInvestmentAmountReaderRegistry(guidePrinter);
	}

	private static InvestmentTypeReader createInvestTypeReaderDelegator(GuidePrinter guidPrinter) {
		return new InvestmentTypeInputReader(guidPrinter);
	}

	private static InvestmentAmountReaderDelegator createInvestmentAmountReaderDelegator(
		InvestmentAmountReaderRegistry investmentAmountReaderRegistry) {
		return new RegistryBasedInvestmentAmountDelegator(
			investmentAmountReaderRegistry);
	}

	private static PeriodTypeInputReader createPeriodTypeReaderDelegator(GuidePrinter guidPrinter) {
		return new PeriodTypeInputReader(guidPrinter);
	}
}
