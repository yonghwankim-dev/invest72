package application;

import domain.investment.Investment;

public interface InvestmentFactory {
	Investment createBy(CalculateInvestmentRequest request);
}
