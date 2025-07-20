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
import adapter.console.CalculateTargetAchievementRunner;
import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.builder.DefaultInvestmentRequestBuilder;
import application.builder.InvestmentRequestBuilder;
import application.config.AppRunnerConfig;
import application.delegator.CalculateInvestmentReaderDelegator;
import application.delegator.InvestmentReaderDelegator;
import application.delegator.TargetAchievementReaderDelegator;
import application.factory.ExpirationInvestmentFactory;
import application.factory.InvestmentFactory;
import application.factory.InvestmentUseCaseFactory;
import application.factory.MonthlyInvestmentFactory;
import application.factory.UseCaseFactory;
import application.printer.InvestmentResultPrinter;
import application.printer.PrintStreamBasedInvestmentResultPrinter;
import application.printer.PrintStreamBasedTargetAchievementResultPrinter;
import application.printer.TargetAchievementResultPrinter;
import application.reader.CalculateInvestmentRequestReader;
import application.reader.TargetAchievementRequestReader;
import application.registry.InvestmentAmountReaderStrategyRegistry;
import application.registry.MapBasedInvestmentAmountReaderStrategyRegistry;
import application.request.CalculateInvestmentRequest;
import application.request.TargetAchievementRequest;
import application.strategy.FixedDepositAmountReaderStrategy;
import application.strategy.InstallmentSavingAmountReaderStrategy;
import application.strategy.InvestmentAmountReaderStrategy;
import application.time.DateProvider;
import application.time.DefaultDateProvider;
import application.usecase.MonthlyTargetAchievementUseCase;
import application.usecase.TargetAchievementUseCase;
import domain.investment.ExpirationInvestment;
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

	private InvestmentFactory<ExpirationInvestment> investmentFactory() {
		return new ExpirationInvestmentFactory();
	}

	private InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory() {
		return new MonthlyInvestmentFactory();
	}

	private InvestmentReaderDelegator<CalculateInvestmentRequest> calculateInvestmentReaderDelegator() {
		return new CalculateInvestmentReaderDelegator(
			defaultInvestmentRequestBuilder(),
			mapBasedInvestmentAmountReaderStrategyRegistry(),
			calculateInvestmentRequestReader()
		);
	}

	private CalculateInvestmentRequestReader calculateInvestmentRequestReader() {
		return new CalculateInvestmentRequestReader(bufferedReader(), writerBasedGuidePrinter());
	}

	private InvestmentAmountReaderStrategyRegistry mapBasedInvestmentAmountReaderStrategyRegistry() {
		return new MapBasedInvestmentAmountReaderStrategyRegistry(createInvestmentAmountReaderStrategyMap());
	}

	private Map<InvestmentType, InvestmentAmountReaderStrategy> createInvestmentAmountReaderStrategyMap() {
		return Map.of(
			InvestmentType.FIXED_DEPOSIT, new FixedDepositAmountReaderStrategy(writerBasedGuidePrinter()),
			InvestmentType.INSTALLMENT_SAVING, new InstallmentSavingAmountReaderStrategy(writerBasedGuidePrinter())
		);
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

	@Override
	public CalculateTargetAchievementRunner createCalculateTargetAchievementRunner() {
		DateProvider dateProvider = createDefaultDataProvider();
		TargetAchievementUseCase useCase = createMonthlyTargetAchievementUseCase(dateProvider);
		TargetAchievementResultPrinter resultPrinter = createPrintStreamBasedTargetAchievementResultPrinter();
		InvestmentReaderDelegator<TargetAchievementRequest> delegator = createTargetAchievementReaderDelegator();
		return new CalculateTargetAchievementRunner(useCase, resultPrinter, delegator);
	}

	private DateProvider createDefaultDataProvider() {
		return new DefaultDateProvider();
	}

	private TargetAchievementUseCase createMonthlyTargetAchievementUseCase(DateProvider dateProvider) {
		return new MonthlyTargetAchievementUseCase(dateProvider);
	}

	private TargetAchievementResultPrinter createPrintStreamBasedTargetAchievementResultPrinter() {
		return new PrintStreamBasedTargetAchievementResultPrinter(printStream);
	}

	private TargetAchievementRequestReader createTargetAchievementRequestReader() {
		return new TargetAchievementRequestReader(
			bufferedReader(), writerBasedGuidePrinter()
		);
	}

	private TargetAchievementReaderDelegator createTargetAchievementReaderDelegator() {
		return new TargetAchievementReaderDelegator(defaultInvestmentRequestBuilder(),
			createTargetAchievementRequestReader());
	}
}
