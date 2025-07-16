package application.reader;

import java.io.IOException;

import domain.type.InvestmentType;

public interface InvestmentAmountReader {
	String read() throws IOException;

	boolean supports(InvestmentType investmentType);
}
