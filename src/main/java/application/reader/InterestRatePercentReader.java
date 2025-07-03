package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface InterestRatePercentReader {
	double read(BufferedReader reader) throws IOException;
}
