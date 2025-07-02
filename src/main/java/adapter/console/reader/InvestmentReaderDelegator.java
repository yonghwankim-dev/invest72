package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.CalculateInvestmentRequest;
import application.TaxableResolver;

public interface InvestmentReaderDelegator {
	CalculateInvestmentRequest readInvestmentRequest(BufferedReader reader, TaxableResolver taxableResolver) throws
		IOException;
}
