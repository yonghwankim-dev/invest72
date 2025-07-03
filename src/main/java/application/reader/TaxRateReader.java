package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface TaxRateReader {
	double read(BufferedReader reader) throws IOException;
}
