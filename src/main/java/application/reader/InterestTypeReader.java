package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface InterestTypeReader {
	String readInterestType(BufferedReader reader) throws IOException;
}
