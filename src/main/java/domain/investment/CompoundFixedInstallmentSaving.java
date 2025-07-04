package domain.investment;

import domain.interest_rate.InterestRate;
import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;

public class CompoundFixedInstallmentSaving implements Investment {

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
		double result = 0;
		for (int i = 0; i < investPeriod.getMonths(); i++) {
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
}
