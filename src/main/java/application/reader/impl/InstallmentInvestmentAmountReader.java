package application.reader.impl;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.InvestmentAmountReader;
import domain.amount.InvestmentAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.amount.YearlyInstallmentInvestmentAmount;
import domain.type.InvestmentType;

public class InstallmentInvestmentAmountReader implements InvestmentAmountReader {

	private final GuidePrinter printer;
	private final BufferedReader reader;

	public InstallmentInvestmentAmountReader(GuidePrinter printer, BufferedReader reader) {
		this.printer = printer;
		this.reader = reader;
	}

	@Override
	public String read() throws IOException {
		printer.printInstallmentInvestmentInputGuide();
		return reader.readLine();
	}

	private InvestmentAmount parseInvestmentAmount(String line) {
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

	@Override
	public boolean supports(InvestmentType investmentType) {
		return investmentType == InvestmentType.INSTALLMENT_SAVING;
	}
}
