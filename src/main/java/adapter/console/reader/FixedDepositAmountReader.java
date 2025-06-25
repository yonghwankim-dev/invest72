package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InvestmentAmount;

public class FixedDepositAmountReader implements InvestmentAmountReader {

	private final PrintStream out;

	public FixedDepositAmountReader(PrintStream out) {
		this.out = out;
	}

	@Override
	public InvestmentAmount read(BufferedReader reader) throws IOException {
		out.print("예치 금액(원)을 입력하세요: ");
		int amount = Integer.parseInt(reader.readLine());
		return new FixedDepositAmount(amount);
	}
}
