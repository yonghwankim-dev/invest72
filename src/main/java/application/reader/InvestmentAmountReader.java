package application.reader;

import java.io.IOException;

import domain.type.InvestmentType;

public interface InvestmentAmountReader {
	String readAmount() throws IOException;

	boolean supports(InvestmentType investmentType);
}
