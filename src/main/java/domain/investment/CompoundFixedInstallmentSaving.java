package domain.investment;

import domain.amount.InstallmentInvestmentAmount;
import domain.interest_rate.InterestRate;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;

public class CompoundFixedInstallmentSaving implements Investment, MonthlyInvestment {

	private final InstallmentInvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public CompoundFixedInstallmentSaving(InstallmentInvestmentAmount investmentAmount, InvestPeriod investPeriod,
		InterestRate interestRate, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	@Override
	public int getTotalProfit() {
		int preTaxAmount = getPreTaxAmount();
		int interest = preTaxAmount - getTotalPrincipal();
		int tax = taxable.applyTax(interest);
		return preTaxAmount - tax;
	}

	private int getPreTaxAmount() {
		return getPreTaxAmount(investPeriod.getMonths());
	}

	private int getPreTaxAmount(int month) {
		double result = 0;
		for (int i = 0; i < month; i++) {
			result = applyMonthlyInvestmentTo(result);
			result = applyMonthlyInterest(result);
		}
		return (int)result;
	}

	private double applyMonthlyInvestmentTo(double currentBalance) {
		return currentBalance + investmentAmount.getMonthlyAmount();
	}

	private double applyMonthlyInterest(double currentBalance) {
		return currentBalance * getGrowthFactor(interestRate);
	}

	private double getGrowthFactor(InterestRate interestRate) {
		return 1 + interestRate.getMonthlyRate().doubleValue();
	}

	private int getTotalPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getInterest() {
		return getPreTaxAmount() - getTotalPrincipal();
	}

	@Override
	public int getTax() {
		return taxable.applyTax(getInterest());
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
	public int getInterest(int month) {
		if (isOutOfRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return getPreTaxAmount(month) - getPrincipal(month);
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
	public int getFinalMonth() {
		return investPeriod.getMonths();
	}
}
