package domain.investment;

import domain.amount.LumpSumInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;

public class CompoundFixedDeposit implements ExpirationInvestment, MonthlyInvestment {

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

	/**
	 * 예금 투자 금액을 계산하여 원금과 이자를 합한 최종 금액을 반환합니다.
	 * 이자는 복리로 계산되며, 세금이 적용됩니다.
	 * <p>
	 * 이자 = 원금 * (1 + 월이자율) ^ 투자기간(개월) - 원금
	 * </p>
	 * <p>
	 * 세금 = 이자 * 세율
	 * </p>
	 * <p>
	 * 최종 금액 = 원금 + 이자 - 세금
	 * </p>
	 */
	@Override
	public int getAmount() {
		int amount = investmentAmount.getDepositAmount();
		int interest = calCompoundInterest();
		int tax = taxable.applyTax(calCompoundInterest());
		return amount + interest - tax;
	}

	private int calCompoundInterest() {
		double totalGrowthFactor = calTotalGrowthFactor();
		return investmentAmount.calCompoundInterest(totalGrowthFactor);
	}

	private double calTotalGrowthFactor() {
		return interestRate.calTotalGrowthFactor(investPeriod);
	}

	@Override
	public int getPrincipalAmount() {
		return investmentAmount.getDepositAmount();
	}

	@Override
	public int getInterest() {
		return calCompoundInterest();
	}

	@Override
	public int getTax() {
		return taxable.applyTax(calCompoundInterest());
	}

	@Override
	public int getAccumulatedPrincipal(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return investmentAmount.getDepositAmount();
	}

	private boolean isOutOfRange(int month) {
		return month < 1 || month > investPeriod.getMonths();
	}

	/**
	 * 월별 복리 누적 이자 계산
	 * 월별 복리 이자 = 월이자(연이율 / 12) * 원금 * (1 + 월이자율)^(month - 1)
	 * @param month 회차 (1부터 시작)
	 * @return 월별 이자 금액
	 */
	@Override
	public int getAccumulatedInterest(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		int result = 0;
		for (int i = 1; i <= month; i++) {
			int monthlyInterest = investmentAmount.calMonthlyInterest(interestRate);
			double growthFactor = interestRate.calGrowthFactor(i);
			result += (int)(monthlyInterest * growthFactor);
		}
		return result;
	}

	@Override
	public int getAccumulatedTax(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return taxable.applyTax(getAccumulatedInterest(month));
	}

	@Override
	public int getAccumulatedTotalProfit(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		int principal = getAccumulatedPrincipal(month);
		int interest = getAccumulatedInterest(month);
		int tax = getAccumulatedTax(month);
		return principal + interest - tax;
	}

	@Override
	public int getFinalMonth() {
		return investPeriod.getMonths();
	}
}
