package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface PeriodReader {
	int readPeriod(BufferedReader reader) throws IOException;
}
