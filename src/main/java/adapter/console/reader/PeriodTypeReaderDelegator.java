package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.type.PeriodType;

public interface PeriodTypeReaderDelegator {
	PeriodType read(BufferedReader reader) throws IOException;
}
