import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import co.invest72.achievement.application.CalculateAchievement;
import co.invest72.achievement.console.CalculateAchievementConsoleRunner;
import co.invest72.achievement.console.input.delegator.TargetAchievementReaderDelegator;
import co.invest72.achievement.console.input.reader.TargetAchievementRequestReader;
import co.invest72.achievement.console.output.PrintStreamBasedTargetAchievementResultPrinter;
import co.invest72.achievement.console.output.TargetAchievementResultPrinter;
import co.invest72.achievement.domain.time.AchievementInvestmentCalculator;
import co.invest72.achievement.domain.time.RealTimeAchievementDateCalculator;
import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.application.InvestmentFactory;
import co.invest72.investment.console.CalculateExpirationInvestmentConsoleRunner;
import co.invest72.investment.console.CalculateMonthlyInvestmentConsoleRunner;
import co.invest72.investment.console.input.delegator.CalculateExpirationInvestmentReaderDelegator;
import co.invest72.investment.console.input.reader.CalculateInvestmentRequestReader;
import co.invest72.investment.console.input.registry.MapBasedInvestmentAmountReaderStrategyRegistry;
import co.invest72.investment.console.input.strategy.FixedDepositAmountReaderStrategy;
import co.invest72.investment.console.input.strategy.InstallmentSavingAmountReaderStrategy;
import co.invest72.investment.console.input.strategy.InvestmentAmountReaderStrategy;
import co.invest72.investment.console.output.InvestmentResultPrinter;
import co.invest72.investment.console.output.PrintStreamBasedInvestmentResultPrinter;
import co.invest72.investment.console.output.guide.BufferedWriterBasedGuidePrinter;
import co.invest72.investment.console.output.guide.GuidePrinter;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.TaxableResolver;
import co.invest72.investment.domain.investment.InvestmentType;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.tax.resolver.KoreanStringBasedTaxableResolver;

public class Invest72Application {
	public static void main(String[] args) {
		createCalculateExpirationInvestmentConsoleRunner().run();
		createCalculateMonthlyInvestmentConsoleRunner().run();
		createCalculateAchievementConsoleRunner().run();
	}

	private static CalculateExpirationInvestmentConsoleRunner createCalculateExpirationInvestmentConsoleRunner() {
		GuidePrinter guidePrinter = createGuidePrinter();
		Map<InvestmentType, InvestmentAmountReaderStrategy> amountReaderStrategies = createAmountReaderStrategies(
			guidePrinter);
		MapBasedInvestmentAmountReaderStrategyRegistry registry = new MapBasedInvestmentAmountReaderStrategyRegistry(
			amountReaderStrategies);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		CalculateInvestmentRequestReader reader = new CalculateInvestmentRequestReader(bufferedReader, guidePrinter);
		CalculateExpirationInvestmentReaderDelegator delegator = new CalculateExpirationInvestmentReaderDelegator(
			registry, reader
		);
		InvestmentResultPrinter printer = new PrintStreamBasedInvestmentResultPrinter(System.out);
		InvestmentFactory factory = new InvestmentFactory();
		CalculateExpirationInvestment useCase = new CalculateExpirationInvestment(factory);
		return new CalculateExpirationInvestmentConsoleRunner(
			System.err,
			delegator,
			printer,
			useCase
		);
	}

	private static CalculateMonthlyInvestmentConsoleRunner createCalculateMonthlyInvestmentConsoleRunner() {
		GuidePrinter guidePrinter = createGuidePrinter();
		Map<InvestmentType, InvestmentAmountReaderStrategy> amountReaderStrategies = createAmountReaderStrategies(
			guidePrinter);
		MapBasedInvestmentAmountReaderStrategyRegistry registry = new MapBasedInvestmentAmountReaderStrategyRegistry(
			amountReaderStrategies);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		CalculateInvestmentRequestReader reader = new CalculateInvestmentRequestReader(bufferedReader, guidePrinter);
		CalculateExpirationInvestmentReaderDelegator delegator = new CalculateExpirationInvestmentReaderDelegator(
			registry, reader
		);
		InvestmentResultPrinter printer = new PrintStreamBasedInvestmentResultPrinter(System.out);
		InvestmentFactory factory = new InvestmentFactory();
		CalculateMonthlyInvestment useCase = new CalculateMonthlyInvestment(factory);
		return new CalculateMonthlyInvestmentConsoleRunner(
			System.err,
			delegator,
			printer,
			useCase
		);
	}

	private static Map<InvestmentType, InvestmentAmountReaderStrategy> createAmountReaderStrategies(
		GuidePrinter guidePrinter) {
		return Map.of(
			InvestmentType.FIXED_DEPOSIT, new FixedDepositAmountReaderStrategy(guidePrinter),
			InvestmentType.INSTALLMENT_SAVING, new InstallmentSavingAmountReaderStrategy(guidePrinter)
		);
	}

	private static GuidePrinter createGuidePrinter() {
		return new BufferedWriterBasedGuidePrinter(
			new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8))
		);
	}

	private static CalculateAchievementConsoleRunner createCalculateAchievementConsoleRunner() {
		TargetAchievementResultPrinter printer = new PrintStreamBasedTargetAchievementResultPrinter(System.out);
		BufferedReader bufferedReader = new BufferedReader(
			new InputStreamReader(System.in, StandardCharsets.UTF_8));
		TargetAchievementRequestReader reader = new TargetAchievementRequestReader(bufferedReader,
			createGuidePrinter());
		TargetAchievementReaderDelegator delegator = new TargetAchievementReaderDelegator(reader);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		CalculateAchievement useCase = new CalculateAchievement(
			new RealTimeAchievementDateCalculator(),
			new KoreanStringBasedTaxableResolver(new KoreanTaxableFactory()),
			new AchievementInvestmentCalculator(taxableResolver)
		);
		return new CalculateAchievementConsoleRunner(
			printer,
			delegator,
			useCase
		);
	}
}
