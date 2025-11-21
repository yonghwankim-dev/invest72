package co.invest72.investment.domain.investment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestPeriod;
import co.invest72.investment.domain.Investment;
import co.invest72.investment.domain.LumpSumInvestmentAmount;
import co.invest72.investment.domain.Taxable;
import lombok.Builder;

public class CompoundFixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	@Builder
	public CompoundFixedDeposit(LumpSumInvestmentAmount investmentAmount, InvestPeriod investPeriod,
		InterestRate interestRate,
		Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.investPeriod = investPeriod;
		this.taxable = taxable;
	}

	@Override
	public int getInvestment() {
		return investmentAmount.getDepositAmount();
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

	/**
	 * 지정된 월 회차(month)까지의 누적 이자 금액을 반환합니다.
	 *
	 * @param month 회차 (1부터 시작)
	 * @return 이자 금액=원금×(1+월이자율)^개월수−원금
	 */
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
		return month < 0 || month > investPeriod.getMonths();
	}

	@Override
	public int getProfit() {
		return getProfit(investPeriod.getMonths());
	}

	@Override
	public int getProfit(int month) {
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
