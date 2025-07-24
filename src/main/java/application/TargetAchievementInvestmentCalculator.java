package application;

import domain.amount.InstallmentInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.investment.CompoundFixedInstallmentSaving;
import domain.investment.Investment;
import domain.tax.Taxable;

public class TargetAchievementInvestmentCalculator implements InvestmentCalculator {

	private final int targetAmount;
	private final InstallmentInvestmentAmount investmentAmount;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public TargetAchievementInvestmentCalculator(int targetAmount, InstallmentInvestmentAmount investmentAmount,
		InterestRate interestRate, Taxable taxable) {
		this.targetAmount = targetAmount;
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	@Override
	public int calMonth() {
		int month = 1;
		int totalProfit = 0;

		while (totalProfit < targetAmount) {
			InvestPeriod investPeriod = new MonthlyInvestPeriod(month);
			Investment investment = new CompoundFixedInstallmentSaving(
				investmentAmount,
				investPeriod,
				interestRate,
				taxable
			);
			totalProfit = investment.getTotalProfit();
			month++;
		}
		return month - 1;
	}
}
