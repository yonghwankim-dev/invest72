package application.parser;

import domain.amount.FixedDepositAmount;
import domain.amount.InvestmentAmount;

public class FixedDepositInvestmentAmountParser implements InvestmentAmountParser {
	@Override
	public InvestmentAmount parse(String line) {
		return new FixedDepositAmount(Integer.parseInt(line.trim()));
	}
}
