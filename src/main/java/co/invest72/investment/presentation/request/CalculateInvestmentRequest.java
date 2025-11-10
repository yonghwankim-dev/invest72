package co.invest72.investment.presentation.request;

import java.util.Objects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Getter
public class CalculateInvestmentRequest {
	private String type;
	private String amountType;
	private int amount;
	private String periodType;
	private int periodValue;
	private String interestType;
	private double annualInterestRate;
	private String taxType;
	private double taxRate;

	@Builder
	private CalculateInvestmentRequest(String type, String amountType, int amount, String periodType, int periodValue,
		String interestType, double annualInterestRate, String taxType, double taxRate) {
		this.type = Objects.requireNonNull(type, "type must not be null");
		this.amountType = Objects.requireNonNull(amountType, "amountType must not be null");
		this.amount = amount;
		this.periodType = Objects.requireNonNull(periodType, "periodType must not be null");
		this.periodValue = periodValue;
		this.interestType = Objects.requireNonNull(interestType, "interestType must not be null");
		this.annualInterestRate = annualInterestRate;
		this.taxType = Objects.requireNonNull(taxType, "taxType must not be null");
		this.taxRate = taxRate;
	}

	@Override
	public String toString() {
		return "CalculateInvestmentRequest[" +
			"type=" + type + ", " +
			"amountType=" + amountType + ", " +
			"amount=" + amount + ", " +
			"periodType=" + periodType + ", " +
			"periodValue=" + periodValue + ", " +
			"interestType=" + interestType + ", " +
			"annualInterestRate=" + annualInterestRate + ", " +
			"taxType=" + taxType + ", " +
			"taxRate=" + taxRate + ']';
	}
}
