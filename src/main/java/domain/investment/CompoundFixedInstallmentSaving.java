package domain.investment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import domain.amount.InstallmentInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;

public class CompoundFixedInstallmentSaving implements Investment, MonthlyInvestment {

	private final InstallmentInvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public CompoundFixedInstallmentSaving(InstallmentInvestmentAmount investmentAmount, InvestPeriod investPeriod,
		InterestRate interestRate, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	@Override
	public int getPrincipal() {
		return getPrincipal(investPeriod.getMonths());
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
	public int getInterest() {
		return getInterest(investPeriod.getMonths());
	}

	/**
	 * 월 회차에 해당하는 이자를 계산합니다.
	 * <p>
	 * 이자 계산은 복리 방식으로, 매월 납입하는 금액에 대해 이자를 계산합니다.
	 * </p>
	 * <p>
	 * 이자 = 매월 납입액 * ((1 + 월 이자율) ^ 회차 - 1) / 월 이자율 * (1 + 월 이자율) - 원금
	 * </p>
	 * @param month 회차 (1부터 시작)
	 * @return 해당 회차의 이자
	 */
	@Override
	public int getInterest(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}

		BigDecimal monthlyAmount = BigDecimal.valueOf(investmentAmount.getMonthlyAmount());
		BigDecimal monthlyRate = interestRate.getMonthlyRate();
		BigDecimal growthFactor = interestRate.calGrowthFactor();
		BigDecimal totalGrowthFactor = interestRate.calTotalGrowthFactor(month);
		BigDecimal principal = BigDecimal.valueOf(getPrincipal(month));

		return totalGrowthFactor.subtract(BigDecimal.ONE, MathContext.DECIMAL64)
			.divide(monthlyRate, MathContext.DECIMAL64)
			.multiply(growthFactor, MathContext.DECIMAL64)
			.multiply(monthlyAmount, MathContext.DECIMAL64)
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
		return taxable.applyTax(getInterest(month));
	}

	@Override
	public int getTotalProfit() {
		return getTotalProfit(investPeriod.getMonths());
	}

	@Override
	public int getTotalProfit(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		int principal = getPrincipal(month);
		int interest = getInterest(month);
		int tax = getTax(month);
		return principal + interest - tax;
	}

	@Override
	public int getFinalMonth() {
		return investPeriod.getMonths();
	}
}
