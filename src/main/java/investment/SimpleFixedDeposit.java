package investment;

import interest_rate.InterestRate;
import invest_amount.LumpSumInvestmentAmount;
import invest_period.InvestPeriod;
import tax.Taxable;

/**
 * 정기 예금
 * 단리로 이자를 계산하며, 세금이 적용됩니다.
 */
public class SimpleFixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final InterestRate interestRate;
	private final InvestPeriod investPeriod;
	private final Taxable taxable;

	public SimpleFixedDeposit(LumpSumInvestmentAmount investmentAmount, InterestRate interestRate, InvestPeriod investPeriod, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.investPeriod = investPeriod;
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
		return (int)(interest * investPeriod.getRemainingPeriodInYears(0));
	}

	private int applyTax(int interest) {
		return taxable.applyTax(interest);
	}
}
