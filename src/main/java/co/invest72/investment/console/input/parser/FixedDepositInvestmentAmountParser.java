package co.invest72.investment.console.input.parser;

import co.invest72.investment.domain.InvestmentAmount;
import co.invest72.investment.domain.amount.FixedDepositAmount;

public class FixedDepositInvestmentAmountParser implements InvestmentAmountParser {
	@Override
	public InvestmentAmount parse(String line) {
		int value = Integer.parseInt(line.split(" ")[1]);
		return new FixedDepositAmount(value);
	}
}
