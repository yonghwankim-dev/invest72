package adapter.console.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.Map;

import adapter.InvestmentApplicationRunner;
import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.config.AppRunnerConfig;
import application.delegator.CalculateExpirationInvestmentReaderDelegator;
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
import application.strategy.FixedDepositAmountReaderStrategy;
import application.strategy.InstallmentSavingAmountReaderStrategy;
import application.strategy.InvestmentAmountReaderStrategy;
import co.invest72.achievement.application.CalculateAchievement;
import co.invest72.achievement.console.ConsoleCalculateAchievementRunner;
import co.invest72.achievement.domain.AchievementDateCalculator;
import co.invest72.achievement.domain.time.AchievementInvestmentCalculator;
import co.invest72.achievement.domain.time.RealTimeAchievementDateCalculator;
import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.application.dto.CalculateInvestmentRequest;
import co.invest72.investment.console.ConsoleCalculateExpirationInvestmentRunner;
import co.invest72.investment.console.ConsoleCalculateMonthlyInvestmentRunner;
import co.invest72.investment.domain.TaxableResolver;
import co.invest72.investment.domain.investment.ExpirationInvestmentFactory;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.tax.resolver.KoreanStringBasedTaxableResolver;

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
	public ConsoleCalculateExpirationInvestmentRunner createCalculateInvestmentRunner() {
		CalculateExpirationInvestment investment = createCalculateExpirationInvestment(
			createExpirationInvestmentFactory());
		return new ConsoleCalculateExpirationInvestmentRunner(errorStream, calculateInvestmentReaderDelegator(),
			createPrintStreamBasedInvestmentResultPrinter(), investment);
	}

	private CalculateExpirationInvestment createCalculateExpirationInvestment(ExpirationInvestmentFactory factory) {
		return new CalculateExpirationInvestment(factory);
	}

	private static ExpirationInvestmentFactory createExpirationInvestmentFactory() {
		return new ExpirationInvestmentFactory();
	}

	private InvestmentReaderDelegator<CalculateInvestmentRequest> calculateInvestmentReaderDelegator() {
		return new CalculateExpirationInvestmentReaderDelegator(
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
		return new ConsoleCalculateMonthlyInvestmentRunner(
			errorStream,
			calculateInvestmentReaderDelegator(),
			createPrintStreamBasedInvestmentResultPrinter()
		);
	}

	@Override
	public ConsoleCalculateAchievementRunner createCalculateTargetAchievementRunner() {
		AchievementDateCalculator achievementDateCalculator = createDefaultDataProvider();
		TaxableResolver taxableResolver = createTaxableResolver();
		AchievementInvestmentCalculator calculator = createTargetAchievementInvestmentCalculator();
		CalculateAchievement useCase = createCalculateAchievement(achievementDateCalculator, taxableResolver,
			calculator);
		TargetAchievementResultPrinter resultPrinter = createPrintStreamBasedTargetAchievementResultPrinter();
		InvestmentReaderDelegator<CalculateAchievement.AchievementRequest> delegator = createTargetAchievementReaderDelegator();
		return new ConsoleCalculateAchievementRunner(useCase, resultPrinter, delegator);
	}

	private AchievementDateCalculator createDefaultDataProvider() {
		return new RealTimeAchievementDateCalculator();
	}

	private CalculateAchievement createCalculateAchievement(AchievementDateCalculator achievementDateCalculator,
		TaxableResolver taxableResolver, AchievementInvestmentCalculator calculator) {
		return new CalculateAchievement(achievementDateCalculator, taxableResolver, calculator);
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
