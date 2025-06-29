package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;
import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_amount.YearlyInstallmentInvestmentAmount;
import domain.type.InvestmentType;

public class InstallmentInvestmentAmountReader implements InvestmentAmountReader {

	private final GuidePrinter printer;

	public InstallmentInvestmentAmountReader(GuidePrinter printer) {
		this.printer = printer;
	}

	@Override
	public InvestmentAmount read(BufferedReader reader) throws IOException {
		printer.printInstallmentInvestmentInputGuide();

		String line = reader.readLine();
		String[] parts = line.split(" ");
		if (parts.length != 2) {
			throw new IllegalArgumentException("투자 기간 단위와 금액을 올바르게 입력해주세요.");
		}
		String periodType = parts[0];
		if (!periodType.equals("월") && !periodType.equals("년")) {
			throw new IllegalArgumentException("투자 기간 단위는 '월' 또는 '년'이어야 합니다.");
		} else if (periodType.equals("월")) {
			int amount = Integer.parseInt(parts[1]);
			return new MonthlyInstallmentInvestmentAmount(amount); // 월 단위는 연 단위로 변환
		} else {
			int amount = Integer.parseInt(parts[1]);
			return new YearlyInstallmentInvestmentAmount(amount); // 년 단위 그대로 사용
		}
	}

	@Override
	public boolean supports(InvestmentType investmentType) {
		return investmentType == InvestmentType.INSTALLMENT_SAVING;
	}
}
