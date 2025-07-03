package application.parser;

import domain.invest_amount.InvestmentAmount;

public interface InvestmentAmountParser {
	InvestmentAmount parse(String line);
}
