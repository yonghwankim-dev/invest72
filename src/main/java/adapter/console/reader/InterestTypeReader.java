package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface InterestTypeReader {
	String read(BufferedReader reader) throws IOException;
}
