package adapter.console.reader;

import java.io.IOException;

public interface InvestmentAmountReaderStrategy {
	String readAmount(InvestReader reader) throws IOException;
}
