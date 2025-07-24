package application;

import application.request.TargetAchievementRequest;
import application.resolver.TaxableResolver;
import domain.amount.InstallmentInvestmentAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.tax.FixedTaxRate;
import domain.tax.TaxRate;
import domain.tax.Taxable;
import domain.type.TaxType;

public class TargetAchievementInvestmentCalculator implements InvestmentCalculator {

	private final TaxableResolver taxableResolver;

	public TargetAchievementInvestmentCalculator(TaxableResolver taxableResolver) {
		this.taxableResolver = taxableResolver;
	}

	@Override
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
