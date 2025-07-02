package application;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import adapter.console.reader.FixedDepositInvestmentAmountParser;
import adapter.console.reader.InstallmentInvestmentAmountParser;
import adapter.console.reader.InvestmentAmountParser;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodMonthsRange;
import domain.invest_period.PeriodRange;
import domain.invest_period.RemainingPeriodProvider;
import domain.investment.CompoundFixedDeposit;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedDeposit;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class DefaultInvestmentFactory implements InvestmentFactory {

	private final Map<InvestmentKey, Function<CalculateInvestmentRequest, Investment>> registry = new HashMap<>();

	public DefaultInvestmentFactory() {
		registry.put(new InvestmentKey(FIXED_DEPOSIT, SIMPLE), this::simpleFixedDeposit);
		registry.put(new InvestmentKey(FIXED_DEPOSIT, COMPOUND), this::compoundFixedDeposit);
		registry.put(new InvestmentKey(INSTALLMENT_SAVING, SIMPLE), this::simpleFixedInstallmentSaving);
		registry.put(new InvestmentKey(INSTALLMENT_SAVING, COMPOUND), this::compoundFixedInstallmentSaving);
	}

	@Override
	public Investment createBy(CalculateInvestmentRequest request) {
		InvestmentType type = InvestmentType.from(request.type());
		InterestType interestType = request.interestType();
		InvestmentKey key = new InvestmentKey(type, interestType);
		Function<CalculateInvestmentRequest, Investment> creator = registry.get(key);
		if (creator == null) {
			throw new IllegalArgumentException("Unsupported investment type or interest type: " + key);
		}
		return creator.apply(request);
	}

	private Investment simpleFixedDeposit(CalculateInvestmentRequest request) {
		// todo: extract
		PeriodRange periodRange = new PeriodMonthsRange(request.investPeriod().getMonths());
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(periodRange);

		InvestmentAmountParser investmentAmountParser = new FixedDepositInvestmentAmountParser();
		LumpSumInvestmentAmount investmentAmount = (LumpSumInvestmentAmount)investmentAmountParser.parse(
			request.amount());
		return new SimpleFixedDeposit(
			investmentAmount,
			remainingPeriodProvider,
			request.interestRate(),
			request.taxable()
		);
	}

	private CompoundFixedDeposit compoundFixedDeposit(CalculateInvestmentRequest request) {
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(Integer.parseInt(request.amount()));
		return new CompoundFixedDeposit(
			investmentAmount,
			request.investPeriod(),
			request.interestRate(),
			request.taxable()
		);
	}

	private SimpleFixedInstallmentSaving simpleFixedInstallmentSaving(CalculateInvestmentRequest request) {
		// todo: extract
		InvestmentAmountParser investmentAmountParser = new InstallmentInvestmentAmountParser();
		InstallmentInvestmentAmount investmentAmount = (InstallmentInvestmentAmount)investmentAmountParser.parse(
			request.amount());
		return new SimpleFixedInstallmentSaving(
			investmentAmount,
			request.investPeriod(),
			request.interestRate(),
			request.taxable()
		);
	}

	private CompoundFixedInstallmentSaving compoundFixedInstallmentSaving(CalculateInvestmentRequest request) {
		// todo: extract
		InvestmentAmountParser investmentAmountParser = new InstallmentInvestmentAmountParser();
		InstallmentInvestmentAmount investmentAmount = (InstallmentInvestmentAmount)investmentAmountParser.parse(
			request.amount());
		return new CompoundFixedInstallmentSaving(
			investmentAmount,
			request.investPeriod(),
			request.interestRate(),
			request.taxable()
		);
	}
}
