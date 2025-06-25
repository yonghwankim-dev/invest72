import java.io.PrintStream;
import java.util.List;

import adapter.console.ConsoleInvestmentRunner;
import adapter.console.reader.ConsoleInvestmentAmountReaderDelegator;
import adapter.console.reader.FixedDepositAmountReader;
import adapter.console.reader.InstallmentInvestmentAmountReader;
import adapter.console.reader.InvestmentAmountReader;
import adapter.console.reader.InvestmentAmountReaderDelegator;
import application.CalculateInvestmentUseCase;
import application.DefaultInvestmentFactory;
import application.InvestmentFactory;
import application.InvestmentUseCase;

public class Invest72Application {
	public static void main(String[] args) {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		PrintStream out = System.out;
		List<InvestmentAmountReader> investmentAmountReaders = List.of(
			new FixedDepositAmountReader(out),
			new InstallmentInvestmentAmountReader(out)
		);
		InvestmentAmountReaderDelegator investmentAmountReaderDelegator = new ConsoleInvestmentAmountReaderDelegator(
			investmentAmountReaders);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, System.in, out,
			investmentAmountReaderDelegator);
		runner.run();
	}
}
