import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Map;

import adapter.InvestmentApplicationRunner;
import adapter.console.CalculateInvestmentRunner;
import adapter.console.reader.BufferedReaderBasedInvestReader;
import adapter.console.reader.CalculateInvestmentReaderDelegator;
import adapter.console.reader.FixedDepositAmountReaderStrategy;
import adapter.console.reader.InstallmentSavingAmountReaderStrategy;
import adapter.console.reader.InvestReader;
import adapter.console.reader.InvestmentAmountReaderStrategy;
import adapter.console.reader.InvestmentAmountReaderStrategyRegistry;
import adapter.console.reader.InvestmentReaderDelegator;
import adapter.console.reader.MapBasedInvestmentAmountReaderStrategyRegistry;
import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;
import application.DefaultInvestmentFactory;
import application.DefaultInvestmentRequestBuilder;
import application.InvestmentFactory;
import application.InvestmentRequestBuilder;
import application.InvestmentUseCaseFactory;
import application.KoreanStringBasedTaxableResolver;
import application.TaxableResolver;
import application.UseCaseFactory;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InvestmentType;

public class Invest72Application {
	public static void main(String[] args) {
		InvestmentApplicationRunner runner = createConsoleBasedInvestmentCalculateRunner();
		runner.run();
	}

	private static InvestmentApplicationRunner createConsoleBasedInvestmentCalculateRunner() {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		UseCaseFactory useCaseFactory = new InvestmentUseCaseFactory(investmentFactory);
		InputStream in = System.in;
		PrintStream out = System.out;
		PrintStream err = System.err;

		GuidePrinter guidPrinter = createGuidPrinter(out);
		InvestmentReaderDelegator investmentReaderDelegator = createInvestmentReaderDelegator(guidPrinter, in);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		return new CalculateInvestmentRunner(
			useCaseFactory,
			in,
			out,
			err,
			investmentReaderDelegator,
			taxableResolver);
	}

	private static InvestmentReaderDelegator createInvestmentReaderDelegator(
		GuidePrinter guidPrinter,
		InputStream in) {
		InvestmentRequestBuilder requestBuilder = new DefaultInvestmentRequestBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		InvestReader investReader = new BufferedReaderBasedInvestReader(reader, guidPrinter);
		Map<InvestmentType, InvestmentAmountReaderStrategy> amountReaderStrategies = Map.of(
			InvestmentType.FIXED_DEPOSIT, new FixedDepositAmountReaderStrategy(),
			InvestmentType.INSTALLMENT_SAVING, new InstallmentSavingAmountReaderStrategy()
		);
		InvestmentAmountReaderStrategyRegistry amountReaderStrategyRegistry =
			new MapBasedInvestmentAmountReaderStrategyRegistry(amountReaderStrategies);
		return new CalculateInvestmentReaderDelegator(
			investReader, requestBuilder, amountReaderStrategyRegistry
		);
	}

	private static GuidePrinter createGuidPrinter(PrintStream out) {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		return new WriterBasedGuidePrinter(bufferedWriter);
	}
}
