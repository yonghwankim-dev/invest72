package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface PeriodReaderDelegator {
	int read(BufferedReader reader) throws IOException;
}
