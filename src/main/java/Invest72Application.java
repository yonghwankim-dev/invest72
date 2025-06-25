import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import adapter.console.ConsoleInvestmentRunner;
import adapter.console.reader.ConsoleInvestmentAmountReaderDelegator;
import adapter.console.reader.InvestmentAmountReaderDelegator;
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

		InvestmentAmountReaderRegistry investmentAmountReaderRegistry = createInvestmentAmountReaderRegistry(out);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = new ConsoleInvestmentAmountReaderDelegator(
			investmentAmountReaderRegistry);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, in, out, investmentAmountReaderDelegator);
		runner.run();
	}

	private static InvestmentAmountReaderRegistry createInvestmentAmountReaderRegistry(PrintStream out) {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		return new DefaultInvestmentAmountReaderRegistry(guidePrinter);
	}
}
