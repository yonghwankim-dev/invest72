package adapter.console;

import static domain.type.InvestmentType.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import application.InvestPeriodFactory;
import application.InvestmentRequest;
import application.InvestmentUseCase;
import application.KoreanStringBasedInvestPeriodFactory;
import application.KoreanStringBasedTaxableResolver;
import application.TaxableResolver;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_amount.YearlyInstallmentInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class ConsoleInvestmentRunner {
	private final InvestmentUseCase useCase;
	private final InputStream in;
	private final PrintStream out;

	public ConsoleInvestmentRunner(InvestmentUseCase useCase, InputStream in, PrintStream out) {
		this.useCase = useCase;
		this.in = in;
		this.out = out;
	}

	public void run() {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			out.print("투자 유형을 입력하세요 (예금 or 적금): ");
			String type = reader.readLine();
			InvestmentType investmentType = InvestmentType.from(type);

			InvestmentAmount investmentAmount = inputInvestmentAmount(investmentType, reader);

			out.print("기간 종류를 입력하세요 (월 or 년): ");
			String periodType = reader.readLine();

			out.print("기간을 입력하세요 (숫자): ");
			int period = Integer.parseInt(reader.readLine());

			out.print("이자 방식을 입력하세요 (단리 or 복리): ");
			String interestTypeText = reader.readLine();

			out.print("이자율을 입력하세요 (%): ");
			int interestRatePercent = Integer.parseInt(reader.readLine());

			out.print("과세 유형을 입력하세요 (일반과세, 비과세, 세금우대): ");
			String taxType = reader.readLine();

			out.print("세율을 입력하세요 (세금우대형일 경우 %, 아니면 0): ");
			double taxRate = toRate(Double.parseDouble(reader.readLine()));

			InvestPeriodFactory investPeriodFactory = new KoreanStringBasedInvestPeriodFactory();
			InvestPeriod investPeriod = investPeriodFactory.createBy(periodType, period);

			TaxableFactory taxableFactory = new KoreanTaxableFactory();
			TaxableResolver taxableResolver = new KoreanStringBasedTaxableResolver(taxableFactory);
			Taxable taxable = taxableResolver.resolve(taxType, new FixedTaxRate(taxRate));

			InterestType interestType = InterestType.from(interestTypeText);
			// todo: add factory
			InterestRate interestRate = new AnnualInterestRate(toRate(interestRatePercent));

			InvestmentRequest request = new InvestmentRequest(
				investmentType,
				investmentAmount,
				investPeriod,
				interestType,
				interestRate,
				taxable
			);

			int result = useCase.calAmount(request);
			out.println("total investment amount: " + result + "원");

		} catch (IOException | IllegalArgumentException e) {
			System.err.println("[ERROR] Input Error: " + e.getMessage());
		}
	}

	private InvestmentAmount inputInvestmentAmount(InvestmentType investmentType, BufferedReader reader) throws
		IOException {
		InvestmentAmount investmentAmount;
		if (investmentType == FIXED_DEPOSIT) {
			out.print("예치 금액(원)을 입력하세요: ");
			int amount = Integer.parseInt(reader.readLine());
			return new FixedDepositAmount(amount);
		}
		out.print(getInstallmentSavingInputMenu());
		String line = reader.readLine();
		String[] parts = line.split(" ");
		if (parts.length != 2) {
			throw new IllegalArgumentException("투자 기간 단위와 금액을 올바르게 입력해주세요.");
		}
		String periodType = parts[0];
		int amount = Integer.parseInt(parts[1]);
		if (!periodType.equals("월") && !periodType.equals("년")) {
			throw new IllegalArgumentException("투자 기간 단위는 '월' 또는 '년'이어야 합니다.");
		} else if (periodType.equals("월")) {
			investmentAmount = new MonthlyInstallmentInvestmentAmount(amount); // 월 단위는 연 단위로 변환
		} else {
			investmentAmount = new YearlyInstallmentInvestmentAmount(amount); // 년 단위 그대로 사용
		}
		return investmentAmount;
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

	private double toRate(double value) {
		return value / 100.0;
	}
}
