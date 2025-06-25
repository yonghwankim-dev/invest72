import java.io.PrintStream;

import adapter.console.ConsoleInvestmentRunner;
import adapter.console.reader.ConsoleInvestmentAmountReaderDelegator;
import adapter.console.reader.InvestmentAmountReaderDelegator;
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
		PrintStream out = System.out;
		InvestmentAmountReaderRegistry investmentAmountReaderRegistry = new DefaultInvestmentAmountReaderRegistry(out);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = new ConsoleInvestmentAmountReaderDelegator(
			investmentAmountReaderRegistry.getReaders(), investmentAmountReaderRegistry);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, System.in, out,
			investmentAmountReaderDelegator);
		runner.run();
	}
}
