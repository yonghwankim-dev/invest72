package application.parser;

import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InvestmentAmount;

public class FixedDepositInvestmentAmountParser implements InvestmentAmountParser {
	@Override
	public InvestmentAmount parse(String line) {
		return new FixedDepositAmount(Integer.parseInt(line.trim()));
	}
}
