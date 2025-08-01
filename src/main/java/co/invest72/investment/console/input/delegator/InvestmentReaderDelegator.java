package co.invest72.investment.console.input.delegator;

import java.io.IOException;

public interface InvestmentReaderDelegator<R> {
	R readRequest() throws IOException;
}
