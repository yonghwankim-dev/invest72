package adapter.console.reader;

import domain.invest_amount.InvestmentAmount;

public interface InvestmentAmountParser {
	InvestmentAmount parse(String line);
}
