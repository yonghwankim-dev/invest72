package application.parser;

import co.invest72.investment.domain.InvestmentAmount;

public interface InvestmentAmountParser {
	InvestmentAmount parse(String line);
}
