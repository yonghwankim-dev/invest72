package application;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;

import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.investment.CompoundFixedDeposit;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedDeposit;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class DefaultInvestmentFactory implements InvestmentRequestFactory {

	@Override
	public Investment createBy(InvestmentRequest request) {
		InvestmentType type = request.getType();
		InterestType interestType = request.getInterestType();
		if (type == FIXED_DEPOSIT && interestType == SIMPLE) {
			return simpleFixedDeposit(request);
		}
		if (type == FIXED_DEPOSIT && interestType == COMPOUND) {
			return compoundFixedDeposit(request);
		}
		if (type == INSTALLMENT_SAVINGS && interestType == SIMPLE) {
			return simpleFixedInstallmentSaving(request);
		}
		if (type == INSTALLMENT_SAVINGS && interestType == COMPOUND) {
			return compoundFixedInstallmentSaving(request);
		}
		throw new IllegalArgumentException("Invalid investment type or interest type: " + type + ", " + interestType);
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
