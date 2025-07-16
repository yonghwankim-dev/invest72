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
import adapter.console.CalculateMonthlyInvestmentApplicationRunner;
import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.builder.DefaultInvestmentRequestBuilder;
import application.builder.InvestmentRequestBuilder;
import application.config.AppRunnerConfig;
import application.delegator.CalculateInvestmentReaderDelegator;
import application.delegator.InvestmentReaderDelegator;
import application.factory.DefaultInvestmentFactory;
import application.factory.InvestmentFactory;
import application.factory.InvestmentUseCaseFactory;
import application.factory.MonthlyInvestmentFactory;
import application.factory.UseCaseFactory;
import application.printer.InvestmentResultPrinter;
import application.printer.PrintStreamBasedInvestmentResultPrinter;
import application.reader.CalculateInvestmentRequestReader;
import application.reader.InvestReader;
import application.reader.impl.BufferedReaderBasedInvestReader;
import application.registry.InvestmentAmountReaderStrategyRegistry;
import application.registry.MapBasedInvestmentAmountReaderStrategyRegistry;
import application.request.CalculateInvestmentRequest;
import application.strategy.FixedDepositAmountReaderStrategy;
import application.strategy.InstallmentSavingAmountReaderStrategy;
import application.strategy.InvestmentAmountReaderStrategy;
import domain.investment.Investment;
import domain.investment.MonthlyInvestment;
import domain.type.InvestmentType;

public class ConsoleAppRunnerConfig implements AppRunnerConfig {

	private final InputStream inputStream;
	private final PrintStream printStream;
	private final PrintStream errorStream;

	public ConsoleAppRunnerConfig() {
		this(System.in, System.out, System.err);
	}

	public ConsoleAppRunnerConfig(InputStream inputStream, PrintStream printStream, PrintStream errorStream) {
		this.inputStream = inputStream;
		this.printStream = printStream;
		this.errorStream = errorStream;
	}

	@Override
	public InvestmentApplicationRunner createCalculateInvestmentRunner() {
		return new CalculateInvestmentRunner(errorStream, useCaseFactory(),
			calculateInvestmentReaderDelegator(), createPrintStreamBasedInvestmentResultPrinter()
		);
	}

	private UseCaseFactory useCaseFactory() {
		return new InvestmentUseCaseFactory(investmentFactory(), monthlyInvestmentFactory());
	}

	private InvestmentFactory<Investment> investmentFactory() {
		return new DefaultInvestmentFactory();
	}

	private InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory() {
		return new MonthlyInvestmentFactory();
	}

	private InvestmentReaderDelegator<CalculateInvestmentRequest> calculateInvestmentReaderDelegator() {
		return new CalculateInvestmentReaderDelegator(
			bufferedReaderBasedInvestReader(),
			defaultInvestmentRequestBuilder(),
			mapBasedInvestmentAmountReaderStrategyRegistry(),
			calculateInvestmentRequestReader()
		);
	}

	private CalculateInvestmentRequestReader calculateInvestmentRequestReader() {
		return new CalculateInvestmentRequestReader(bufferedReader());
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
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(printStream));
		return new BufferedWriterBasedGuidePrinter(bufferedWriter, errorStream);
	}

	private InvestmentRequestBuilder defaultInvestmentRequestBuilder() {
		return new DefaultInvestmentRequestBuilder();
	}

	private InvestmentResultPrinter createPrintStreamBasedInvestmentResultPrinter() {
		return new PrintStreamBasedInvestmentResultPrinter(printStream);
	}

	@Override
	public InvestmentApplicationRunner createCalculateMonthlyInvestmentRunner() {
		return new CalculateMonthlyInvestmentApplicationRunner(
			useCaseFactory(),
			errorStream,
			calculateInvestmentReaderDelegator(),
			createPrintStreamBasedInvestmentResultPrinter()
		);
	}
}
