package application.parser;

import java.io.File;

import co.invest72.investment.application.dto.CalculateInvestmentRequest;

public interface CalculateInvestmentRequestParser {
	CalculateInvestmentRequest parse(File file);
}
