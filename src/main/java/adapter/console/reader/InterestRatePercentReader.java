package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface InterestRatePercentReader {
	int read(BufferedReader reader) throws IOException;
}
