package application;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.investment.CompoundFixedDeposit;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedDeposit;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class DefaultInvestmentFactory implements InvestmentFactory {

	private final Map<InvestmentKey, Function<InvestmentRequest, Investment>> registry = new HashMap<>();

	public DefaultInvestmentFactory() {
		registry.put(new InvestmentKey(FIXED_DEPOSIT, SIMPLE), this::simpleFixedDeposit);
		registry.put(new InvestmentKey(FIXED_DEPOSIT, COMPOUND), this::compoundFixedDeposit);
		registry.put(new InvestmentKey(INSTALLMENT_SAVINGS, SIMPLE), this::simpleFixedInstallmentSaving);
		registry.put(new InvestmentKey(INSTALLMENT_SAVINGS, COMPOUND), this::compoundFixedInstallmentSaving);
	}

	@Override
	public Investment createBy(InvestmentRequest request) {
		InvestmentType type = request.getType();
		InterestType interestType = request.getInterestType();
		InvestmentKey key = new InvestmentKey(type, interestType);
		Function<InvestmentRequest, Investment> creator = registry.get(key);
		if (creator == null) {
			throw new IllegalArgumentException("Unsupported investment type or interest type: " + key);
		}
		return creator.apply(request);
	}

	private Investment simpleFixedDeposit(InvestmentRequest request) {
		return new SimpleFixedDeposit(
			(LumpSumInvestmentAmount)request.getAmount(),
			request.getInvestPeriod(),
			request.getInterestRate(),
			request.getTaxable()
		);
	}

	private CompoundFixedDeposit compoundFixedDeposit(InvestmentRequest request) {
		return new CompoundFixedDeposit(
			(LumpSumInvestmentAmount)request.getAmount(),
			request.getInvestPeriod(),
			request.getInterestRate(),
			request.getTaxable()
		);
	}

	private SimpleFixedInstallmentSaving simpleFixedInstallmentSaving(InvestmentRequest request) {
		return new SimpleFixedInstallmentSaving(
			(InstallmentInvestmentAmount)request.getAmount(),
			request.getInvestPeriod(),
			request.getInterestRate(),
			request.getTaxable()
		);
	}

	private CompoundFixedInstallmentSaving compoundFixedInstallmentSaving(InvestmentRequest request) {
		return new CompoundFixedInstallmentSaving(
			(InstallmentInvestmentAmount)request.getAmount(),
			request.getInvestPeriod(),
			request.getInterestRate(),
			request.getTaxable()
		);
	}
}
