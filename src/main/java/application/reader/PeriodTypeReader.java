package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface PeriodTypeReader {
	String read(BufferedReader reader) throws IOException;
}
