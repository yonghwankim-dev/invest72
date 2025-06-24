package application;

import domain.investment.Investment;

public interface InvestmentRequestFactory {
	Investment createBy(InvestmentRequest request);
}
