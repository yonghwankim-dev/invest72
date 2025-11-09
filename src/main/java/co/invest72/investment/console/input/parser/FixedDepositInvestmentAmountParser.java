package co.invest72.investment.console.input.parser;

import co.invest72.investment.domain.InvestmentAmount;
import co.invest72.investment.domain.amount.AmountType;
import co.invest72.investment.domain.amount.FixedDepositAmount;

public class FixedDepositInvestmentAmountParser implements InvestmentAmountParser {
	@Override
	public InvestmentAmount parse(String line) {
		if (!line.split(" ")[0].equals(AmountType.ONE_TIME.getDescription())) {
			throw new IllegalArgumentException("예치 금액은 '일시불'이어야 합니다.");
		}
		int value = Integer.parseInt(line.split(" ")[1]);
		return new FixedDepositAmount(value);
	}
}
