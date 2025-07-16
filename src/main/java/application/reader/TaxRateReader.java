package application.reader;

import java.io.IOException;

public interface TaxRateReader {
	double readTaxRate() throws IOException;
}
