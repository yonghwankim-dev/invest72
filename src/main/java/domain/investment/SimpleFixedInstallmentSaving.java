package domain.investment;

import domain.interest_rate.InterestRate;
import domain.amount.InstallmentInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;

/**
 * 정기적금
 * 이자 계산 방식은 단리 방식으로, 매월 납입하는 금액에 대해 이자를 계산합니다.
 */
public class SimpleFixedInstallmentSaving implements Investment {

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

	/**
	 * 투자 기간 동안 납입한 총 원금과 이자를 합산하고 세금을 차감한 최종 금액을 반환합니다.
	 * 총투자금액 = 총 원금 + 이자 - 세금
	 */
	@Override
	public int getAmount() {
		int totalPrincipal = getTotalPrincipal();
		int interest = calInterest();
		int tax = getTax(interest);
		return totalPrincipal + interest - tax;
	}

	private int calInterest() {
		int amount = investmentAmount.getMonthlyAmount();
		double interestMonthFactor =
			(double)(investPeriod.getMonths() * (investPeriod.getMonths() + 1)) / 2; // 월 가중치 계수
		double monthlyRate = interestRate.getMonthlyRate();
		return (int)(amount * interestMonthFactor * monthlyRate);
	}

	private int getTotalPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	private int getTax(int interest) {
		return taxable.applyTax(interest);
	}

	@Override
	public int getPrincipalAmount() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getInterest() {
		return calInterest();
	}

	@Override
	public int getTax() {
		return getTax(calInterest());
	}

}
