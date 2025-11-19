package co.invest72.investment.domain;

import lombok.Builder;

public class MonthlyCompoundInterest implements Investment {

	private final LumpSumInvestmentAmount initialAmount;
	private final InvestmentAmount monthlyAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	@Builder
	public MonthlyCompoundInterest(LumpSumInvestmentAmount initialAmount, InvestmentAmount monthlyAmount,
		InvestPeriod investPeriod, InterestRate interestRate, Taxable taxable) {
		this.initialAmount = initialAmount;
		this.monthlyAmount = monthlyAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	@Override
	public int getPrincipal() {
		return 0;
	}

	@Override
	public int getPrincipal(int month) {
		return 0;
	}

	@Override
	public int getInterest() {
		return 0;
	}

	@Override
	public int getInterest(int month) {
		return 0;
	}

	@Override
	public int getTax() {
		return 0;
	}

	@Override
	public int getTax(int month) {
		return 0;
	}

	@Override
	public int getTotalProfit() {
		return 0;
	}

	@Override
	public int getTotalProfit(int month) {
		return 0;
	}

	@Override
	public int getFinalMonth() {
		return 0;
	}
}
