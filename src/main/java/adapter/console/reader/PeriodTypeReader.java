package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.type.PeriodType;

public interface PeriodTypeReader {
	PeriodType read(BufferedReader reader) throws IOException;
}
