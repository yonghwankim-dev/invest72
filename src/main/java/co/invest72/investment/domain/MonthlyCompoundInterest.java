package co.invest72.investment.domain;

import java.math.BigDecimal;

import lombok.Builder;

public class MonthlyCompoundInterest implements Investment {

	private final LumpSumInvestmentAmount initialAmount;
	private final InvestmentAmount monthlyAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	@Builder
	public MonthlyCompoundInterest(LumpSumInvestmentAmount initialAmount, InvestmentAmount monthlyAmount,
		InvestPeriod investPeriod, InterestRate interestRate, Taxable taxable) {
		this.initialAmount = initialAmount;
		this.monthlyAmount = monthlyAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	@Override
	public int getPrincipal() {
		return getPrincipal(investPeriod.getMonths());
	}

	/**
	 * 시작 금액 + (월 납입액 * (회차 - 1)) 을 계산합니다.
	 * @param month 회차 (두번째 달부터 원금에 가산됨)
	 * @return 해당 회차의 원금
	 */
	@Override
	public int getPrincipal(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		BigDecimal monthlyPrincipal = BigDecimal.valueOf(monthlyAmount.getAmount().longValue() * (month - 1));
		return initialAmount.addAmount(monthlyPrincipal).intValue();
	}

	private boolean isOutOfRange(int month) {
		return month < 0 || month > investPeriod.getMonths();
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
}
