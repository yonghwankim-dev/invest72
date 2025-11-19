package co.invest72.investment.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import lombok.Builder;

public class MonthlyCompoundInterest implements Investment {

	private final LumpSumInvestmentAmount initialAmount;
	private final InvestmentAmount monthlyAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	@Builder(toBuilder = true)
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
		if (month <= 1) {
			return initialAmount.getAmount().intValue();
		}
		BigDecimal monthlyPrincipal = BigDecimal.valueOf(monthlyAmount.getAmount().longValue() * (month - 1));
		return initialAmount.addAmount(monthlyPrincipal).intValue();
	}

	private boolean isOutOfRange(int month) {
		return month > investPeriod.getMonths();
	}

	@Override
	public int getInterest() {
		return getInterest(investPeriod.getMonths());
	}

	@Override
	public int getInterest(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		if (month <= 1) {
			return 0;
		}
		BigDecimal monthlyInvestmentAmount = this.monthlyAmount.getAmount();
		BigDecimal monthlyRate = interestRate.getMonthlyRate();
		BigDecimal growthFactor = interestRate.calGrowthFactor();
		BigDecimal totalGrowthFactor = interestRate.calTotalGrowthFactor(month - 1);
		BigDecimal principal = BigDecimal.valueOf(getPrincipal(month));

		return totalGrowthFactor.subtract(BigDecimal.ONE, MathContext.DECIMAL64)
			.divide(monthlyRate, MathContext.DECIMAL64)
			.multiply(growthFactor, MathContext.DECIMAL64)
			.multiply(monthlyInvestmentAmount, MathContext.DECIMAL64)
			.subtract(principal, MathContext.DECIMAL64)
			.setScale(0, RoundingMode.HALF_EVEN)
			.intValueExact();
	}

	@Override
	public int getTax() {
		return getTax(investPeriod.getMonths());
	}

	@Override
	public int getTax(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		if (month <= 1) {
			return 0;
		}
		return taxable.applyTax(getInterest(month));
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
