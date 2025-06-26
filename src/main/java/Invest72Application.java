import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import adapter.console.ConsoleInvestmentRunner;
import adapter.console.reader.AnnualInterestRateReader;
import adapter.console.reader.ConsoleInterestTypeReader;
import adapter.console.reader.ConsoleInvestmentReaderDelegator;
import adapter.console.reader.ConsolePeriodReaderDelegator;
import adapter.console.reader.ConsolePeriodTypeReaderDelegator;
import adapter.console.reader.ConsoleTaxTypeReader;
import adapter.console.reader.FixedTaxRateReader;
import adapter.console.reader.InterestRatePercentReader;
import adapter.console.reader.InterestTypeReader;
import adapter.console.reader.InvestmentAmountReaderDelegator;
import adapter.console.reader.InvestmentReaderDelegator;
import adapter.console.reader.InvestmentTypeInputReader;
import adapter.console.reader.InvestmentTypeReader;
import adapter.console.reader.PeriodReaderDelegator;
import adapter.console.reader.PeriodTypeReaderDelegator;
import adapter.console.reader.RegistryBasedInvestmentAmountReader;
import adapter.console.reader.TaxRateReader;
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
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		InputStream in = System.in;
		PrintStream out = System.out;

		GuidePrinter guidPrinter = createGuidPrinter(out);
		InvestmentAmountReaderRegistry investmentAmountReaderRegistry = createInvestmentAmountReaderRegistry(
			guidPrinter);
		InvestmentTypeReader investmentTypeReaderDelegator = createInvestTypeReaderDelegator(
			guidPrinter);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = createInvestmentAmountReaderDelegator(
			investmentAmountReaderRegistry);
		PeriodTypeReaderDelegator periodTypeReaderDelegator = createPeriodTypeReaderDelegator(guidPrinter);
		PeriodReaderDelegator periodReaderDelegator = new ConsolePeriodReaderDelegator(guidPrinter);
		InterestTypeReader interestTypeReader = new ConsoleInterestTypeReader(guidPrinter);
		InterestRatePercentReader interestRatePercentReader = new AnnualInterestRateReader(guidPrinter);
		TaxTypeReader taxTypeReader = new ConsoleTaxTypeReader(guidPrinter);
		TaxRateReader taxRateReader = new FixedTaxRateReader(guidPrinter);
		InvestmentReaderDelegator investmentReaderDelegator = new ConsoleInvestmentReaderDelegator(
			investmentTypeReaderDelegator,
			investmentAmountReaderDelegator,
			periodTypeReaderDelegator,
			periodReaderDelegator,
			interestTypeReader,
			interestRatePercentReader,
			taxTypeReader,
			taxRateReader
		);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(
			useCase,
			in,
			out,
			investmentReaderDelegator,
			taxableResolver
		);
		runner.run();
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
		return new RegistryBasedInvestmentAmountReader(
			investmentAmountReaderRegistry);
	}

	private static ConsolePeriodTypeReaderDelegator createPeriodTypeReaderDelegator(GuidePrinter guidPrinter) {
		return new ConsolePeriodTypeReaderDelegator(guidPrinter);
	}
}
