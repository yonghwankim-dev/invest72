package application.delegator;

import java.io.IOException;

public interface InvestmentReaderDelegator<R> {
	R readInvestmentRequest() throws IOException;
}
