package application.parser;

import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_amount.YearlyInstallmentInvestmentAmount;

public class InstallmentInvestmentAmountParser implements InvestmentAmountParser {

	@Override
	public InvestmentAmount parse(String line) {
		String[] parts = line.split(" ");
		if (parts.length != 2) {
			throw new IllegalArgumentException("투자 기간 단위와 금액을 올바르게 입력해주세요.");
		}
		String periodType = parts[0];
		if (!periodType.equals("월") && !periodType.equals("년")) {
			throw new IllegalArgumentException("투자 기간 단위는 '월' 또는 '년'이어야 합니다.");
		} else if (periodType.equals("월")) {
			int amount = Integer.parseInt(parts[1]);
			return new MonthlyInstallmentInvestmentAmount(amount);
		} else {
			int amount = Integer.parseInt(parts[1]);
			return new YearlyInstallmentInvestmentAmount(amount);
		}
	}
}
