package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface InvestmentTypeReader {
	String read(BufferedReader reader) throws IOException;
}
