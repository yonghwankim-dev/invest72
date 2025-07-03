package adapter.console.config;

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
import adapter.console.ui.WriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.DefaultInvestmentFactory;
import application.DefaultInvestmentRequestBuilder;
import application.InvestmentFactory;
import application.InvestmentRequestBuilder;
import application.InvestmentUseCaseFactory;
import application.UseCaseFactory;
import application.config.AppRunnerConfig;
import domain.type.InvestmentType;

public class ConsoleAppRunnerConfig implements AppRunnerConfig {

	private final InputStream inputStream;
	private final PrintStream printStream;
	private final PrintStream errorStream;

	public ConsoleAppRunnerConfig(InputStream inputStream, PrintStream printStream, PrintStream errorStream) {
		this.inputStream = inputStream;
		this.printStream = printStream;
		this.errorStream = errorStream;
	}

	@Override
	public InvestmentApplicationRunner createCalculateInvestmentRunner() {
		return new CalculateInvestmentRunner(
			printStream, errorStream, useCaseFactory(),
			calculateInvestmentReaderDelegator()
		);
	}

	private UseCaseFactory useCaseFactory() {
		return new InvestmentUseCaseFactory(defaultInvestmentFactory());
	}

	private InvestmentFactory defaultInvestmentFactory() {
		return new DefaultInvestmentFactory();
	}

	private InvestmentReaderDelegator calculateInvestmentReaderDelegator() {
		return new CalculateInvestmentReaderDelegator(
			bufferedReaderBasedInvestReader(),
			defaultInvestmentRequestBuilder(),
			mapBasedInvestmentAmountReaderStrategyRegistry()
		);
	}

	private InvestmentAmountReaderStrategyRegistry mapBasedInvestmentAmountReaderStrategyRegistry() {
		return new MapBasedInvestmentAmountReaderStrategyRegistry(createInvestmentAmountReaderStrategyMap());
	}

	private Map<InvestmentType, InvestmentAmountReaderStrategy> createInvestmentAmountReaderStrategyMap() {
		return Map.of(
			InvestmentType.FIXED_DEPOSIT, new FixedDepositAmountReaderStrategy(),
			InvestmentType.INSTALLMENT_SAVING, new InstallmentSavingAmountReaderStrategy()
		);
	}

	private InvestReader bufferedReaderBasedInvestReader() {
		BufferedReader bufferedReader = bufferedReader();
		GuidePrinter guidePrinter = writerBasedGuidePrinter();
		return new BufferedReaderBasedInvestReader(bufferedReader, guidePrinter);
	}

	private BufferedReader bufferedReader() {
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	private GuidePrinter writerBasedGuidePrinter() {
		return new WriterBasedGuidePrinter(new BufferedWriter(new OutputStreamWriter(printStream)));
	}

	private InvestmentRequestBuilder defaultInvestmentRequestBuilder() {
		return new DefaultInvestmentRequestBuilder();
	}
}
