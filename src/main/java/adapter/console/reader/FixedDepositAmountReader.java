package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

public class FixedDepositAmountReader implements InvestmentAmountReader {

	private final GuidePrinter printer;

	public FixedDepositAmountReader(GuidePrinter printer) {
		this.printer = printer;
	}

	@Override
	public InvestmentAmount read(BufferedReader reader) throws IOException {
		printer.printFixedDepositAmountInputGuide();
		int amount = Integer.parseInt(reader.readLine());
		return new FixedDepositAmount(amount);
	}

	@Override
	public boolean supports(InvestmentType investmentType) {
		return investmentType == InvestmentType.FIXED_DEPOSIT;
	}
}
