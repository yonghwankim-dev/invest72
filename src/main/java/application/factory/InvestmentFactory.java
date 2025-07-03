package application.factory;

import application.request.CalculateInvestmentRequest;
import domain.investment.Investment;

public interface InvestmentFactory {
	Investment createBy(CalculateInvestmentRequest request);
}
