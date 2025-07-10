package application.parser;

import domain.amount.InvestmentAmount;

public interface InvestmentAmountParser {
	InvestmentAmount parse(String line);
}
