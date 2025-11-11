package co.invest72.investment.console.input.parser;

import java.io.File;

import co.invest72.investment.presentation.request.CalculateInvestmentRequest;

public interface CalculateInvestmentRequestParser {
	CalculateInvestmentRequest parse(File file);
}
