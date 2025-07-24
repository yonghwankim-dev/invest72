package domain.investment;

import domain.amount.InstallmentInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.tax.Taxable;

public class MonthlyTargetAchievementInvestment implements TargetAchievementInvestment {

	@Override
	public int getPrincipal() {
		return 0;
	}

	@Override
	public int getPrincipal(int month) {
		return 0;
	}

	@Override
	public int getInterest() {
		return 0;
	}

	@Override
	public int getInterest(int month) {
		return 0;
	}

	@Override
	public int getTax() {
		return 0;
	}

	@Override
	public int getTax(int month) {
		return 0;
	}

	@Override
	public int getTotalProfit() {
		return 0;
	}

	@Override
	public int getTotalProfit(int month) {
		return 0;
	}

	@Override
	public int getFinalMonth() {
		return 0;
	}

	@Override
	public int calMonth(int targetAmount, InstallmentInvestmentAmount investmentAmount, InterestRate interestRate,
		Taxable taxable) {
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
		return month - 1; // 마지막에 증가된 month를 제외하고 반환
	}
}
