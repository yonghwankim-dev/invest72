package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface InvestmentTypeReader {
	String readInvestmentType(BufferedReader reader) throws IOException;
}
