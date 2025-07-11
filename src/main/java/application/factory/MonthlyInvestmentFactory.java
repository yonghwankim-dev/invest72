package application.factory;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import application.key.InvestmentKey;
import application.parser.FixedDepositInvestmentAmountParser;
import application.parser.InvestmentAmountParser;
import application.request.CalculateInvestmentRequest;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodMonthsRange;
import domain.invest_period.PeriodRange;
import domain.invest_period.PeriodYearRange;
import domain.invest_period.RemainingPeriodProvider;
import domain.investment.MonthlyInvestment;
import domain.investment.SimpleFixedDeposit;
import domain.tax.FixedTaxRate;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InterestType;
import domain.type.InvestmentType;
import domain.type.PeriodType;
import domain.type.TaxType;

public class MonthlyInvestmentFactory implements InvestmentFactory<MonthlyInvestment> {

	private final Map<InvestmentKey, Function<CalculateInvestmentRequest, MonthlyInvestment>> registry = new HashMap<>();

	public MonthlyInvestmentFactory() {
		registry.put(new InvestmentKey(FIXED_DEPOSIT, SIMPLE), this::simpleFixedDeposit);
	}

	@Override
	public MonthlyInvestment createBy(CalculateInvestmentRequest request) {
		InvestmentKey key = createInvestmentKey(request.type(), request.interestType());
		Function<CalculateInvestmentRequest, MonthlyInvestment> creator = registry.get(key);
		if (creator == null) {
			throw new IllegalArgumentException("Unsupported investment type or interest type: " + key);
		}
		return creator.apply(request);
	}

	private InvestmentKey createInvestmentKey(String investmentTypeValue, String interestTypeValue) {
		InvestmentType type = InvestmentType.from(investmentTypeValue);
		InterestType interestType = InterestType.from(interestTypeValue);
		return new InvestmentKey(type, interestType);
	}

	private SimpleFixedDeposit simpleFixedDeposit(CalculateInvestmentRequest request) {
		// todo: extract
		PeriodType periodType = PeriodType.from(request.periodType());
		PeriodRange periodRange = createPeriodRange(periodType, request.periodValue());
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(periodRange);

		InvestmentAmountParser investmentAmountParser = new FixedDepositInvestmentAmountParser();
		LumpSumInvestmentAmount investmentAmount = (LumpSumInvestmentAmount)investmentAmountParser.parse(
			request.amount());
		InterestRate interestRate = new AnnualInterestRate(request.annualInterestRate());
		Taxable taxable = resolveTaxable(request);
		return new SimpleFixedDeposit(
			investmentAmount,
			remainingPeriodProvider,
			interestRate,
			taxable
		);
	}

	private Taxable resolveTaxable(CalculateInvestmentRequest request) {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		TaxRate taxRate = new FixedTaxRate(request.taxRate());
		TaxType taxType = TaxType.from(request.taxType());
		return taxableResolver.resolve(taxType, taxRate);
	}

	private static PeriodRange createPeriodRange(PeriodType periodType, int periodValue) {
		PeriodRange periodRange;
		if (periodType == PeriodType.MONTH) {
			periodRange = new PeriodMonthsRange(periodValue);
		} else {
			periodRange = new PeriodYearRange(periodValue);
		}
		return periodRange;
	}
}
