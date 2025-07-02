package application;

import domain.interest_rate.InterestRate;
import domain.invest_amount.InvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.Taxable;
import domain.type.InterestType;
import domain.type.InvestmentType;

public record CalculateInvestmentRequest(InvestmentType type, InvestmentAmount amount, InvestPeriod investPeriod,
										 InterestType interestType, InterestRate interestRate, Taxable taxable) {
}
