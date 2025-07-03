package application.delegator;

import java.io.IOException;

import application.request.CalculateInvestmentRequest;

public interface InvestmentReaderDelegator {
	CalculateInvestmentRequest readInvestmentRequest() throws IOException;
}
