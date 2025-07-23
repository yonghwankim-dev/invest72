package domain.investment;

import domain.amount.InstallmentInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;

/**
 * 정기적금
 * 이자 계산 방식은 단리 방식으로, 매월 납입하는 금액에 대해 이자를 계산합니다.
 */
public class SimpleFixedInstallmentSaving implements Investment, MonthlyInvestment {

	private final InstallmentInvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public SimpleFixedInstallmentSaving(InstallmentInvestmentAmount investmentAmount, InvestPeriod investPeriod,
		InterestRate interestRate, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	/**
	 * 투자 기간 동안 납입한 총 원금과 이자를 합산하고 세금을 차감한 최종 금액을 반환합니다.
	 * 총투자금액 = 총 원금 + 이자 - 세금
	 */
	@Override
	public int getTotalProfit() {
		int totalPrincipal = getTotalPrincipal();
		int interest = calInterest(investPeriod.getMonths());
		int tax = taxable.applyTax(interest);
		return totalPrincipal + interest - tax;
	}

	private int getTotalPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getInterest() {
		return calInterest(investPeriod.getMonths());
	}

	@Override
	public int getTax() {
		return taxable.applyTax(calInterest(investPeriod.getMonths()));
	}

	@Override
	public int getPrincipal(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return investmentAmount.getMonthlyAmount() * month;
	}

	private boolean isOutOfRange(int month) {
		return month < 1 || month > investPeriod.getMonths();
	}

	@Override
	public int getInterest(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return calInterest(month);
	}

	private int calInterest(int month) {
		int amount = investmentAmount.getMonthlyAmount();
		double interestMonthFactor = calInterestMonthFactor(month);
		double monthlyRate = interestRate.getMonthlyRate().doubleValue();
		return (int)(amount * interestMonthFactor * monthlyRate);
	}

	private double calInterestMonthFactor(int month) {
		return (double)(month * (month + 1)) / 2;
	}

	@Override
	public int getTax(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return taxable.applyTax(calInterest(month));
	}

	@Override
	public int getTotalProfit(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return getPrincipal(month) + getInterest(month) - getTax(month);
	}

	@Override
	public int getFinalMonth() {
		return investPeriod.getMonths();
	}
}
