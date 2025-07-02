package adapter.console.reader;

import java.io.IOException;

import application.CalculateInvestmentRequest;
import application.TaxableResolver;

public interface InvestmentReaderDelegator {
	CalculateInvestmentRequest readInvestmentRequest(TaxableResolver taxableResolver) throws
		IOException;
}
