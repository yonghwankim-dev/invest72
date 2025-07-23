package domain.investment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import domain.amount.LumpSumInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;

public class CompoundFixedDeposit implements Investment, MonthlyInvestment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public CompoundFixedDeposit(LumpSumInvestmentAmount investmentAmount, InvestPeriod investPeriod,
		InterestRate interestRate,
		Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.investPeriod = investPeriod;
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

		return depositAmount.multiply(interestRate.calTotalGrowthFactor(month), MathContext.DECIMAL64)
			.subtract(depositAmount)
			.setScale(0, RoundingMode.HALF_EVEN)
			.intValueExact();
	}

	@Override
	public int getTax() {
		return taxable.applyTax(getInterest());
	}
	
	@Override
	public int getTax(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return taxable.applyTax(getInterest(month));
	}

	private boolean isOutOfRange(int month) {
		return month < 1 || month > investPeriod.getMonths();
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
