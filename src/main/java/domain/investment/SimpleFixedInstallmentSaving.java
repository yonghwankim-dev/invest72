package domain.investment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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

	@Override
	public int getPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
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

	@Override
	public int getInterest(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		BigDecimal monthlyAmount = BigDecimal.valueOf(investmentAmount.getMonthlyAmount());
		BigDecimal monthlyRate = interestRate.getMonthlyRate();
		BigDecimal accumulationFactor = calInterestMonthFactor(month);
		return monthlyAmount.multiply(monthlyRate, MathContext.DECIMAL64)
			.multiply(accumulationFactor, MathContext.DECIMAL64)
			.setScale(0, RoundingMode.HALF_EVEN)
			.intValueExact();
	}

	private BigDecimal calInterestMonthFactor(int month) {
		BigDecimal m = BigDecimal.valueOf(month);
		BigDecimal numerator = m.multiply(m.add(BigDecimal.ONE)); // month * (month + 1)
		return numerator.divide(BigDecimal.valueOf(2), MathContext.DECIMAL64);
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
	public int getTotalProfit(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return getPrincipal(month) + getInterest(month) - getTax(month);
	}

	@Override
	public int getTotalProfit() {
		int totalPrincipal = getPrincipal();
		int interest = getInterest();
		int tax = getTax();
		return totalPrincipal + interest - tax;
	}

	@Override
	public int getFinalMonth() {
		return investPeriod.getMonths();
	}
}
