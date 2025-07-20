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
	public int getAmount() {
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
		return 1 + interestRate.getMonthlyRate();
	}

	private int getTotalPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getPrincipalAmount() {
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
	public int getAccumulatedPrincipal(int month) {
		if (isInNotRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return investmentAmount.getMonthlyAmount() * month;
	}

	private boolean isInNotRange(int month) {
		return month < 1 || month > investPeriod.getMonths();
	}

	@Override
	public int getAccumulatedInterest(int month) {
		if (isInNotRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return getPreTaxAmount(month) - getAccumulatedPrincipal(month);
	}

	@Override
	public int getAccumulatedTax(int month) {
		if (isInNotRange(month)) {
			throw new IllegalArgumentException("Invalid month: " + month);
		}
		return taxable.applyTax(getAccumulatedInterest(month));
	}

	@Override
	public int getAccumulatedTotalProfit(int month) {
		return 0;
	}

	@Override
	public int getFinalMonth() {
		return 0;
	}
}
