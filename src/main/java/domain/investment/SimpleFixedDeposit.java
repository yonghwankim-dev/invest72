package domain.investment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import domain.amount.LumpSumInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.invest_period.RemainingPeriodProvider;
import domain.tax.Taxable;

/**
 * 정기 예금
 * 단리로 이자를 계산하며, 세금이 적용됩니다.
 */
public class SimpleFixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final RemainingPeriodProvider remainingPeriodProvider;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public SimpleFixedDeposit(LumpSumInvestmentAmount investmentAmount, RemainingPeriodProvider remainingPeriodProvider,
		InvestPeriod investPeriod, InterestRate interestRate, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.remainingPeriodProvider = remainingPeriodProvider;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	@Override
	public int getPrincipal() {
		return investmentAmount.getDepositAmount();
	}

	@Override
	public int getPrincipal(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return investmentAmount.getDepositAmount();
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
		BigDecimal depositAmount = BigDecimal.valueOf(investmentAmount.getDepositAmount());
		BigDecimal monthlyRate = interestRate.getMonthlyRate();
		BigDecimal monthDecimal = BigDecimal.valueOf(month);

		return depositAmount.multiply(monthlyRate, MathContext.DECIMAL64)
			.multiply(monthDecimal, MathContext.DECIMAL64)
			.setScale(0, RoundingMode.HALF_EVEN)
			.intValueExact();
	}

	@Override
	public int getTax() {
		return applyTax(getInterest());
	}

	private int applyTax(int interest) {
		return taxable.applyTax(interest);
	}

	@Override
	public int getTax(int month) {
		return applyTax(getInterest(month));
	}

	@Override
	public int getTotalProfit(int month) {
		int principal = getPrincipal(month);
		int interest = getInterest(month);
		int tax = getTax(month);
		return principal + interest - tax;
	}
	
	@Override
	public int getTotalProfit() {
		return getTotalProfit(remainingPeriodProvider.getFinalMonth());
	}

	@Override
	public int getFinalMonth() {
		return remainingPeriodProvider.getFinalMonth();
	}
}
