package application;

import static domain.type.InterestType.*;
import static domain.type.InvestmentType.*;

import domain.interest_rate.InterestRate;
import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.investment.CompoundFixedDeposit;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedDeposit;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class DefaultInvestmentFactory implements InvestmentRequestFactory {

	@Override
	public Investment createBy(InvestmentRequest request) {
		// 5. 투자 객체 생성
		return createInvestment(request);
	}

	private Investment createInvestment(InvestmentRequest request) {
		// 단리 or 복리
		InvestmentType type = request.getType();
		InterestType interestType = request.getInterestType();
		if (type == FIXED_DEPOSIT && interestType == SIMPLE) {
			return new SimpleFixedDeposit(
				(LumpSumInvestmentAmount)request.getAmount(),
				createInterestRate(request),
				request.getInvestPeriod(),
				createTaxable(request)
			);
		}
		if (type == FIXED_DEPOSIT && interestType == COMPOUND) {
			return new CompoundFixedDeposit(
				(LumpSumInvestmentAmount)request.getAmount(),
				createInterestRate(request),
				request.getInvestPeriod(),
				createTaxable(request)
			);
		}
		if (type == INSTALLMENT_SAVINGS && interestType == SIMPLE) {
			return new SimpleFixedInstallmentSaving(
				(InstallmentInvestmentAmount)request.getAmount(),
				request.getInvestPeriod(),
				createInterestRate(request),
				createTaxable(request)
			);
		}
		if (type == INSTALLMENT_SAVINGS && interestType == COMPOUND) {
			return new CompoundFixedInstallmentSaving(
				(InstallmentInvestmentAmount)request.getAmount(),
				request.getInvestPeriod(),
				createInterestRate(request),
				createTaxable(request)
			);
		}
		throw new IllegalArgumentException(
			"Invalid investment type or interest type: " + type + ", " + interestType
		);
	}

	private InterestRate createInterestRate(InvestmentRequest request) {
		return request.getInterestRate();
	}

	private Taxable createTaxable(InvestmentRequest request) {
		TaxableFactory factory = new KoreanTaxableFactory();
		if (request.getTaxType().equals("일반과세")) {
			return factory.createStandardTax();
		} else if (request.getTaxType().equals("비과세")) {
			return factory.createNonTax();
		} else if (request.getTaxType().equals("세금우대형")) {
			return factory.createTaxBenefit(request.getTaxRatePercent() / 100.0);
		}
		throw new IllegalArgumentException("Invalid tax type: " + request.getTaxType());
	}
}
