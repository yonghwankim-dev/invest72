package application;

import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class InvestmentRequest {
	private final InvestmentType type;
	private final InvestmentAmount amount;
	private final InvestPeriod investPeriod;
	private final InterestType interestType;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public InvestmentRequest(
		InvestmentType type,
		InvestmentAmount amount,
		InvestPeriod investPeriod,
		InterestType interestType,
		InterestRate interestRate,
		Taxable taxable
	) {
		this.type = type;
		this.amount = amount;
		this.investPeriod = investPeriod;
		this.interestType = interestType;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	public InvestmentType getType() {
		return type;
	}

	public InvestmentAmount getAmount() {
		return amount;
	}

	public InvestPeriod getInvestPeriod() {
		return investPeriod;
	}

	public InterestType getInterestType() {
		return interestType;
	}

	public InterestRate getInterestRate() {
		return interestRate;
	}

	public Taxable getTaxable() {
		return taxable;
	}
}
