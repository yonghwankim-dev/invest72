package application;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_amount.YearlyInstallmentInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.investment.CompoundFixedDeposit;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.investment.SimpleFixedDeposit;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

public class DefaultInvestmentFactory implements InvestmentRequestFactory {

	@Override
	public Investment createBy(InvestmentRequest request) {
		// 5. 투자 객체 생성
		return createInvestment(request);
	}


	private Investment createInvestment(InvestmentRequest request) {
		// 단리 or 복리
		String type = request.getType();
		String interestType = request.getInterestType();
		if (type.equals("예금") && interestType.equals("단리")) {
			return new SimpleFixedDeposit(
				(LumpSumInvestmentAmount) createInvestmentAmount(request),
				createInterestRate(request),
				createInvestPeriod(request),
				createTaxable(request)
			);
		}
		if (type.equals("예금") && interestType.equals("복리")) {
			return new CompoundFixedDeposit(
				(LumpSumInvestmentAmount) createInvestmentAmount(request),
				createInterestRate(request),
				createInvestPeriod(request),
				createTaxable(request)
			);
		}
		if (type.equals("적금") && interestType.equals("단리")) {
			return new SimpleFixedInstallmentSaving(
				(InstallmentInvestmentAmount)createInvestmentAmount(request),
				createInvestPeriod(request),
				createInterestRate(request),
				createTaxable(request)
			);
		}
		if (type.equals("적금") && interestType.equals("복리")) {
			return new CompoundFixedInstallmentSaving(
				(InstallmentInvestmentAmount)createInvestmentAmount(request),
				createInvestPeriod(request),
				createInterestRate(request),
				createTaxable(request)
			);
		}
		throw new IllegalArgumentException(
			"Invalid investment type or interest type: " + type + ", " + interestType
		);
	}

	private InvestmentAmount createInvestmentAmount(InvestmentRequest request) {
		// 예치금액, 적금 월나입액, 적금 연납입액
		int amount = request.getAmount();
		if (request.getType().equals("예금")) {
			return new FixedDepositAmount(amount);
		}
		if (request.getType().equals("적금")) {
			if (request.getPeriodType().equals("month")) {
				// 월납입액
				return new MonthlyInstallmentInvestmentAmount(amount);
			} else if (request.getPeriodType().equals("year")) {
				// 연납입액
				return new YearlyInstallmentInvestmentAmount(amount);
			}
		}
		throw new IllegalArgumentException(
			"Invalid investment type or period type: " + request.getType() + ", " + request.getPeriodType()
		);
	}

	private InterestRate createInterestRate(InvestmentRequest request) {
		return new AnnualInterestRate(request.getInterestRatePercent() / 100.0);
	}

	private InvestPeriod createInvestPeriod(InvestmentRequest request) {
		if (request.getPeriodType().equals("month")) {
			return new MonthlyInvestPeriod(request.getPeriod());
		} else if (request.getPeriodType().equals("year")) {
			return new YearlyInvestPeriod(request.getPeriod());
		}
		throw new IllegalArgumentException("Invalid period type: " + request.getPeriodType());
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
