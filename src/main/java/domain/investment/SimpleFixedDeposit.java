package domain.investment;

import domain.interest_rate.InterestRate;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.invest_period.RemainingPeriodProvider;
import domain.tax.Taxable;

/**
 * 정기 예금
 * 단리로 이자를 계산하며, 세금이 적용됩니다.
 */
public class SimpleFixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final RemainingPeriodProvider remainingPeriodProvider;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public SimpleFixedDeposit(LumpSumInvestmentAmount investmentAmount, RemainingPeriodProvider remainingPeriodProvider,
		InterestRate interestRate,
		Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.remainingPeriodProvider = remainingPeriodProvider;
		this.taxable = taxable;
	}

	/**
	 * 투자 금액 = 원금 + 이자 - 세금
	 */
	@Override
	public int getAmount() {
		int amount = investmentAmount.getDepositAmount();
		int interest = calInterest();
		int tax = applyTax(interest);
		return amount + interest - tax;
	}

	/**
	 * 단리 이자 계산
	 * 이자 = 투자금액 * 연이율 * 투자기간(년)
	 */
	private int calInterest() {
		double interest = investmentAmount.calAnnualInterest(interestRate);
		return (int)(interest * remainingPeriodProvider.calRemainingPeriodInYears(0));
	}

	private int applyTax(int interest) {
		return taxable.applyTax(interest);
	}

	@Override
	public int getPrincipalAmount() {
		return investmentAmount.getDepositAmount();
	}

	@Override
	public int getInterest() {
		return calInterest();
	}

	@Override
	public int getTax() {
		return applyTax(calInterest());
	}
}
