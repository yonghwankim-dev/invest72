package application.parser;

import java.io.File;

import application.request.CalculateInvestmentRequest;

public interface CalculateInvestmentRequestParser {
	CalculateInvestmentRequest parse(File file);
}
