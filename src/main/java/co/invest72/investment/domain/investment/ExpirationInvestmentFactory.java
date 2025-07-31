package co.invest72.investment.domain.investment;

import static co.invest72.investment.domain.interest.InterestType.*;
import static co.invest72.investment.domain.investment.InvestmentType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import application.parser.FixedDepositInvestmentAmountParser;
import application.parser.InstallmentInvestmentAmountParser;
import application.parser.InvestmentAmountParser;
import application.request.CalculateInvestmentRequest;
import application.resolver.KoreanStringBasedTaxableResolver;
import application.resolver.TaxableResolver;
import co.invest72.investment.domain.InstallmentInvestmentAmount;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.LumpSumInvestmentAmount;
import co.invest72.investment.domain.PeriodRange;
import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.TaxableFactory;
import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.period.MonthlyInvestPeriod;
import co.invest72.investment.domain.period.PeriodMonthsRange;
import co.invest72.investment.domain.period.PeriodType;
import co.invest72.investment.domain.period.PeriodYearRange;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;
import co.invest72.investment.domain.tax.TaxType;

public class ExpirationInvestmentFactory {

	private final Map<InvestmentKey, Function<CalculateInvestmentRequest, Investment>> registry = new HashMap<>();

	public ExpirationInvestmentFactory() {
		registry.put(new InvestmentKey(FIXED_DEPOSIT, SIMPLE), this::simpleFixedDeposit);
		registry.put(new InvestmentKey(FIXED_DEPOSIT, COMPOUND), this::compoundFixedDeposit);
		registry.put(new InvestmentKey(INSTALLMENT_SAVING, SIMPLE), this::simpleFixedInstallmentSaving);
		registry.put(new InvestmentKey(INSTALLMENT_SAVING, COMPOUND), this::compoundFixedInstallmentSaving);
	}

	public Investment createBy(CalculateInvestmentRequest request) {
		InvestmentKey key = createInvestmentKey(request.type(), request.interestType());
		Function<CalculateInvestmentRequest, Investment> creator = registry.get(key);
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

	private Investment simpleFixedDeposit(CalculateInvestmentRequest request) {
		PeriodType periodType = PeriodType.from(request.periodType());
		PeriodRange periodRange = createPeriodRange(periodType, request.periodValue());
		InvestPeriod investPeriod = new MonthlyInvestPeriod(periodRange.toMonths());

		InvestmentAmountParser investmentAmountParser = new FixedDepositInvestmentAmountParser();
		LumpSumInvestmentAmount investmentAmount = (LumpSumInvestmentAmount)investmentAmountParser.parse(
			request.amount());
		InterestRate interestRate = new AnnualInterestRate(request.annualInterestRate());
		Taxable taxable = resolveTaxable(request);
		return new SimpleFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable);
	}

	private CompoundFixedDeposit compoundFixedDeposit(CalculateInvestmentRequest request) {
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(Integer.parseInt(request.amount()));
		PeriodType periodType = PeriodType.from(request.periodType());
		PeriodRange periodRange = createPeriodRange(periodType, request.periodValue());
		InvestPeriod investPeriod = periodType.create(periodRange);
		InterestRate interestRate = new AnnualInterestRate(request.annualInterestRate());
		Taxable taxable = resolveTaxable(request);
		return new CompoundFixedDeposit(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
	}

	private SimpleFixedInstallmentSaving simpleFixedInstallmentSaving(CalculateInvestmentRequest request) {
		InvestmentAmountParser investmentAmountParser = new InstallmentInvestmentAmountParser();
		InstallmentInvestmentAmount investmentAmount = (InstallmentInvestmentAmount)investmentAmountParser.parse(
			request.amount());
		PeriodType periodType = PeriodType.from(request.periodType());
		PeriodRange periodRange = createPeriodRange(periodType, request.periodValue());
		InvestPeriod investPeriod = periodType.create(periodRange);
		InterestRate interestRate = new AnnualInterestRate(request.annualInterestRate());
		Taxable taxable = resolveTaxable(request);
		return new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
	}

	private CompoundFixedInstallmentSaving compoundFixedInstallmentSaving(CalculateInvestmentRequest request) {
		InvestmentAmountParser investmentAmountParser = new InstallmentInvestmentAmountParser();
		InstallmentInvestmentAmount investmentAmount = (InstallmentInvestmentAmount)investmentAmountParser.parse(
			request.amount());
		PeriodType periodType = PeriodType.from(request.periodType());
		PeriodRange periodRange = createPeriodRange(periodType, request.periodValue());
		InvestPeriod investPeriod = periodType.create(periodRange);
		InterestRate interestRate = new AnnualInterestRate(request.annualInterestRate());
		Taxable taxable = resolveTaxable(request);
		return new CompoundFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
	}

	private PeriodRange createPeriodRange(PeriodType periodType, int periodValue) {
		PeriodRange periodRange;
		if (periodType == PeriodType.MONTH) {
			periodRange = new PeriodMonthsRange(periodValue);
		} else {
			periodRange = new PeriodYearRange(periodValue);
		}
		return periodRange;
	}

	private Taxable resolveTaxable(CalculateInvestmentRequest request) {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
		TaxType taxType = TaxType.from(request.taxType());
		TaxRate taxRate = new FixedTaxRate(request.taxRate());
		return taxableResolver.resolve(taxType, taxRate);
	}

	public record InvestmentKey(InvestmentType investmentType, InterestType interestType) {
	}
}
