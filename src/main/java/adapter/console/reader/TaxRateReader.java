package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.tax.TaxRate;

public interface TaxRateReader {
	TaxRate read(BufferedReader reader) throws IOException;
}
