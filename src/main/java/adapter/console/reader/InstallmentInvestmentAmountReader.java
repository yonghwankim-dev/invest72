package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_amount.YearlyInstallmentInvestmentAmount;
import domain.type.InvestmentType;

public class InstallmentInvestmentAmountReader implements InvestmentAmountReader {

	private final PrintStream out;

	public InstallmentInvestmentAmountReader(PrintStream out) {
		this.out = out;
	}

	@Override
	public InvestmentAmount read(BufferedReader reader) throws IOException {
		printInstallmentSavingInputMenu();

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

	private void printInstallmentSavingInputMenu() {
		out.print(getInstallmentSavingInputMenu());
	}

	private String getInstallmentSavingInputMenu() {
		return "\uD83D\uDCB0 투자 기간 단위와 금액을 한 줄로 입력해주세요.\n"
			+ "\n"
			+ "\uD83D\uDCDD 형식:\n"
			+ "[단위] [투자금액]\n"
			+ "\n"
			+ "\uD83D\uDCCC 단위 예시:\n"
			+ "- \"월\" → 적금 (매월 납입 금액)\n"
			+ "- \"년\" → 예금 (총 예치 금액)\n"
			+ "\n"
			+ "\uD83D\uDCCC 예시 입력:\n"
			+ "- 월 1000000\n"
			+ "- 년 5000000\n"
			+ "\n"
			+ "\uD83D\uDC49 입력: \n";
	}

	@Override
	public boolean supports(InvestmentType investmentType) {
		return investmentType == InvestmentType.INSTALLMENT_SAVING;
	}
}
