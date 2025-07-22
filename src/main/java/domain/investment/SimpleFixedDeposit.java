package domain.investment;

import domain.amount.LumpSumInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.RemainingPeriodProvider;
import domain.tax.Taxable;

/**
 * 정기 예금
 * 단리로 이자를 계산하며, 세금이 적용됩니다.
 */
public class SimpleFixedDeposit implements Investment, MonthlyInvestment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final RemainingPeriodProvider remainingPeriodProvider;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public SimpleFixedDeposit(LumpSumInvestmentAmount investmentAmount, RemainingPeriodProvider remainingPeriodProvider,
		InterestRate interestRate,
		Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.remainingPeriodProvider = remainingPeriodProvider;
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
		return month < 1 || month > remainingPeriodProvider.getFinalMonth();
	}

	@Override
	public int getInterest() {
		double interest = investmentAmount.calAnnualInterest(interestRate);
		return (int)(interest * remainingPeriodProvider.calRemainingPeriodInYears(0));
	}

	@Override
	public int getInterest(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return calAnnualInterest() * month / 12;
	}

	private int calAnnualInterest() {
		return (int)(investmentAmount.getDepositAmount() * interestRate.getAnnualRate());
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
		return getPrincipal(month) + getInterest(month) - getTax(month);
	}

	/**
	 * 투자 금액 = 원금 + 이자 - 세금
	 */
	@Override
	public int getTotalProfit() {
		int amount = getPrincipal();
		int interest = getInterest();
		int tax = getTax();
		return amount + interest - tax;
	}

	@Override
	public int getFinalMonth() {
		return remainingPeriodProvider.getFinalMonth();
	}
}
