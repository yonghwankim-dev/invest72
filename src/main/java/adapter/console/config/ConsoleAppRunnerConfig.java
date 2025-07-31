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
import application.config.AppRunnerConfig;
import application.delegator.CalculateInvestmentReaderDelegator;
import application.delegator.InvestmentReaderDelegator;
import application.delegator.TargetAchievementReaderDelegator;
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
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import application.strategy.FixedDepositAmountReaderStrategy;
import application.strategy.InstallmentSavingAmountReaderStrategy;
import application.strategy.InvestmentAmountReaderStrategy;
import application.time.DateProvider;
import application.time.DefaultDateProvider;
import co.invest72.achievement.application.CalculateAchievement;
import co.invest72.achievement.domain.AchievementInvestmentCalculator;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;

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
		return new CalculateInvestmentRunner(errorStream, calculateInvestmentReaderDelegator(),
			createPrintStreamBasedInvestmentResultPrinter()
		);
	}

	private InvestmentReaderDelegator<CalculateInvestmentRequest> calculateInvestmentReaderDelegator() {
		return new CalculateInvestmentReaderDelegator(
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

	private InvestmentResultPrinter createPrintStreamBasedInvestmentResultPrinter() {
		return new PrintStreamBasedInvestmentResultPrinter(printStream);
	}

	@Override
	public InvestmentApplicationRunner createCalculateMonthlyInvestmentRunner() {
		return new CalculateMonthlyInvestmentApplicationRunner(
			errorStream,
			calculateInvestmentReaderDelegator(),
			createPrintStreamBasedInvestmentResultPrinter()
		);
	}

	@Override
	public CalculateTargetAchievementRunner createCalculateTargetAchievementRunner() {
		DateProvider dateProvider = createDefaultDataProvider();
		TaxableResolver taxableResolver = createTaxableResolver();
		AchievementInvestmentCalculator calculator = createTargetAchievementInvestmentCalculator();
		CalculateAchievement useCase = createCalculateAchievement(dateProvider, taxableResolver,
			calculator);
		TargetAchievementResultPrinter resultPrinter = createPrintStreamBasedTargetAchievementResultPrinter();
		InvestmentReaderDelegator<TargetAchievementRequest> delegator = createTargetAchievementReaderDelegator();
		return new CalculateTargetAchievementRunner(useCase, resultPrinter, delegator);
	}

	private DateProvider createDefaultDataProvider() {
		return new DefaultDateProvider();
	}

	private CalculateAchievement createCalculateAchievement(DateProvider dateProvider,
		TaxableResolver taxableResolver, AchievementInvestmentCalculator calculator) {
		return new CalculateAchievement(dateProvider, taxableResolver, calculator);
	}

	private AchievementInvestmentCalculator createTargetAchievementInvestmentCalculator() {
		return new AchievementInvestmentCalculator(createTaxableResolver());
	}

	private TaxableResolver createTaxableResolver() {
		return new KoreanStringBasedTaxableResolver(createTaxableFactory());
	}

	private KoreanTaxableFactory createTaxableFactory() {
		return new KoreanTaxableFactory();
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
		return new TargetAchievementReaderDelegator(createTargetAchievementRequestReader());
	}
}
