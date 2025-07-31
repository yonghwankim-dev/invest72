package co.invest72.achievement.domain;

import application.request.TargetAchievementRequest;
import application.resolver.TaxableResolver;
import co.invest72.investment.domain.InstallmentInvestmentAmount;
import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.TaxRate;
import co.invest72.investment.domain.Taxable;
import co.invest72.investment.domain.amount.MonthlyInstallmentInvestmentAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.investment.CompoundFixedInstallmentSaving;
import co.invest72.investment.domain.period.MonthlyInvestPeriod;
import co.invest72.investment.domain.tax.FixedTaxRate;
import co.invest72.investment.domain.tax.TaxType;

public class AchievementInvestmentCalculator {

	private final TaxableResolver taxableResolver;

	public AchievementInvestmentCalculator(TaxableResolver taxableResolver) {
		this.taxableResolver = taxableResolver;
	}

	public int calMonth(TargetAchievementRequest request) {
		int month = 1;
		int totalProfit = 0;
		while (totalProfit < request.targetAmount()) {
			Investment investment = createInvestment(month, request);
			totalProfit = investment.getTotalProfit();
			month++;
		}
		return month - 1;
	}

	private Investment createInvestment(int month, TargetAchievementRequest request) {
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(
			request.monthlyInvestmentAmount());
		InvestPeriod investPeriod = new MonthlyInvestPeriod(month);
		InterestRate interestRate = new AnnualInterestRate(request.interestRate());
		Taxable taxable = resolveTaxable(request);
		return new CompoundFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
	}

	private Taxable resolveTaxable(TargetAchievementRequest request) {
		TaxType taxType = TaxType.from(request.taxType());
		TaxRate taxRate = new FixedTaxRate(request.taxRate());
		return taxableResolver.resolve(taxType, taxRate);
	}
}
