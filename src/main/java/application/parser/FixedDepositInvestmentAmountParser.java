package application.parser;

import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.InvestmentAmount;

public class FixedDepositInvestmentAmountParser implements InvestmentAmountParser {
	@Override
	public InvestmentAmount parse(String line) {
		return new FixedDepositAmount(Integer.parseInt(line.trim()));
	}
}
