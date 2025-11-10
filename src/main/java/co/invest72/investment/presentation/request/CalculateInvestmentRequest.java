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
	private Integer amount;
	private String periodType;
	private Integer periodValue;
	private String interestType;
	private Double annualInterestRate;
	private String taxType;
	private Double taxRate;

	@Builder
	private CalculateInvestmentRequest(String type, String amountType, Integer amount, String periodType,
		Integer periodValue, String interestType, Double annualInterestRate, String taxType, Double taxRate) {
		this.type = Objects.requireNonNull(type, "type must not be null");
		this.amountType = Objects.requireNonNull(amountType, "amountType must not be null");
		this.amount = Objects.requireNonNull(amount, "amount must not be null");
		this.periodType = Objects.requireNonNull(periodType, "periodType must not be null");
		this.periodValue = Objects.requireNonNull(periodValue, "periodValue must not be null");
		this.interestType = Objects.requireNonNull(interestType, "interestType must not be null");
		this.annualInterestRate = Objects.requireNonNull(annualInterestRate, "annualInterestRate must not be null");
		this.taxType = Objects.requireNonNull(taxType, "taxType must not be null");
		this.taxRate = Objects.requireNonNull(taxRate, "taxRate must not be null");
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
