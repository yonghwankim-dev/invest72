package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.type.InvestmentType;

public interface InvestmentTypeReaderDelegator {
	InvestmentType read(BufferedReader reader) throws IOException;
}
