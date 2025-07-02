package adapter.console.reader;

import java.io.IOException;

import application.CalculateInvestmentRequest;
import application.InvestmentRequestBuilder;
import domain.type.InvestmentType;

public class CalculateInvestmentReaderDelegator implements InvestmentReaderDelegator {
	private final InvestReader investReader;
	private final InvestmentRequestBuilder requestBuilder;

	public CalculateInvestmentReaderDelegator(
		InvestReader investReader, InvestmentRequestBuilder requestBuilder
	) {
		this.requestBuilder = requestBuilder;
		this.investReader = investReader;
	}

	@Override
	public CalculateInvestmentRequest readInvestmentRequest() throws IOException {
		String investmentType = investReader.readInvestmentType();
		String investmentAmount = readInvestmentAmount(investmentType);
		String periodType = investReader.readPeriodType();
		int periodValue = investReader.readPeriodValue();
		String interestType = investReader.readInterestType();
		double annualInterestRate = investReader.readAnnualInterestRate();
		String taxType = investReader.readTaxType();
		double taxRate = investReader.readTaxRate();

		CalculateInvestmentRequest.CalculateInvestmentRequestBuilder builder = requestBuilder.calculateInvestmentRequestBuilder();
		return builder.type(investmentType)
			.amount(investmentAmount)
			.periodType(periodType)
			.periodValue(periodValue)
			.interestType(interestType)
			.interestRate(annualInterestRate)
			.taxType(taxType)
			.taxRate(taxRate)
			.build();
	}

	private String readInvestmentAmount(String investmentType) throws IOException {
		if (InvestmentType.FIXED_DEPOSIT.getTypeName().equals(investmentType)) {
			return investReader.readFixedDepositAmount();
		} else if (InvestmentType.INSTALLMENT_SAVING.getTypeName().equals(investmentType)) {
			return investReader.readInstallmentSavingAmount();
		}
		throw new IllegalArgumentException("지원하지 않는 투자 유형입니다: " + investmentType);
	}
}
