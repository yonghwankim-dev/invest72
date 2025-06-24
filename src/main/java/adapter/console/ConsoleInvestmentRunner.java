package adapter.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import application.InvestmentUseCase;

public class ConsoleInvestmentRunner {
	private final InvestmentUseCase useCase;

	public ConsoleInvestmentRunner(InvestmentUseCase useCase) {
		this.useCase = useCase;
	}

	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.print("투자 유형을 입력하세요 (예금 or 적금): ");
			String type = reader.readLine();

			System.out.print("투자 금액을 입력하세요 (숫자): ");
			int amount = Integer.parseInt(reader.readLine());

			System.out.print("기간 종류를 입력하세요 (월 or 년): ");
			String periodType = reader.readLine();

			System.out.print("기간을 입력하세요 (숫자): ");
			int period = Integer.parseInt(reader.readLine());

			System.out.print("이자 방식을 입력하세요 (단리 or 복리): ");
			String interestType = reader.readLine();

			System.out.print("이자율을 입력하세요 (%): ");
			int interestRate = Integer.parseInt(reader.readLine());

			System.out.print("과세 유형을 입력하세요 (일반과세, 비과세, 세금우대형): ");
			String taxType = reader.readLine();

			System.out.print("세율을 입력하세요 (세금우대형일 경우 %, 아니면 0): ");
			double taxRate = Double.parseDouble(reader.readLine());

			// InvestPeriodFactory investPeriodFactory = new KoreanStringBasedInvestPeriodFactory();
			// InvestPeriod investPeriod = investPeriodFactory.createBy(periodType, period);
			//
			// InvestmentRequest request = new InvestmentRequest(
			// 	InvestmentType.from(type),
			// 	amount,
			// 	investPeriod,
			// 	InterestType.from(interestType),
			// 	interestRate,
			// 	TaxType.from(taxType),
			// 	taxRate
			// );
			//
			// int result = useCase.calAmount(request);
			// System.out.println("최종 수령액: " + result + "원");

		} catch (IOException | IllegalArgumentException e) {
			System.err.println("[ERROR] 입력 오류: " + e.getMessage());
		}
	}
}
