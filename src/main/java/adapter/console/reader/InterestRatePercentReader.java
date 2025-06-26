package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.interest_rate.InterestRate;

public interface InterestRatePercentReader {
	InterestRate read(BufferedReader reader) throws IOException;
}
