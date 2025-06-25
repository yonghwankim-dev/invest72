import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import adapter.console.ConsoleInvestmentRunner;
import adapter.console.reader.ConsoleInvestmentAmountReaderDelegator;
import adapter.console.reader.ConsoleInvestmentTypeReaderDelegator;
import adapter.console.reader.ConsolePeriodTypeReaderDelegator;
import adapter.console.reader.InvestmentAmountReaderDelegator;
import adapter.console.reader.InvestmentTypeReaderDelegator;
import adapter.console.reader.PeriodTypeReaderDelegator;
import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;
import application.CalculateInvestmentUseCase;
import application.DefaultInvestmentAmountReaderRegistry;
import application.DefaultInvestmentFactory;
import application.InvestmentAmountReaderRegistry;
import application.InvestmentFactory;
import application.InvestmentUseCase;

public class Invest72Application {
	public static void main(String[] args) {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		InputStream in = System.in;
		PrintStream out = System.out;

		GuidePrinter guidPrinter = createGuidPrinter(out);
		InvestmentAmountReaderRegistry investmentAmountReaderRegistry = createInvestmentAmountReaderRegistry(
			guidPrinter);
		InvestmentTypeReaderDelegator investmentTypeReaderDelegator = createInvestTypeReaderDelegator(
			guidPrinter);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = createInvestmentAmountReaderDelegator(
			investmentAmountReaderRegistry);
		PeriodTypeReaderDelegator periodTypeReaderDelegator = createPeriodTypeReaderDelegator(guidPrinter);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(
			useCase,
			in,
			out,
			investmentTypeReaderDelegator,
			investmentAmountReaderDelegator,
			periodTypeReaderDelegator
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

	private static InvestmentTypeReaderDelegator createInvestTypeReaderDelegator(GuidePrinter guidPrinter) {
		return new ConsoleInvestmentTypeReaderDelegator(guidPrinter);
	}

	private static InvestmentAmountReaderDelegator createInvestmentAmountReaderDelegator(
		InvestmentAmountReaderRegistry investmentAmountReaderRegistry) {
		return new ConsoleInvestmentAmountReaderDelegator(
			investmentAmountReaderRegistry);
	}

	private static ConsolePeriodTypeReaderDelegator createPeriodTypeReaderDelegator(GuidePrinter guidPrinter) {
		return new ConsolePeriodTypeReaderDelegator(guidPrinter);
	}
}
