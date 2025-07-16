package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface InterestRateReader {
	double readInterestRate(BufferedReader reader) throws IOException;
}
