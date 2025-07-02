package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import application.InvestmentRequest;
import application.TaxableResolver;

public interface InvestmentReaderDelegator {
	InvestmentRequest readInvestmentRequest(BufferedReader reader, TaxableResolver taxableResolver) throws IOException;
}
