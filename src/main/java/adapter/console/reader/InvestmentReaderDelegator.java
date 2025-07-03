package adapter.console.reader;

import java.io.IOException;

import application.CalculateInvestmentRequest;

public interface InvestmentReaderDelegator {
	CalculateInvestmentRequest readInvestmentRequest() throws IOException;
}
