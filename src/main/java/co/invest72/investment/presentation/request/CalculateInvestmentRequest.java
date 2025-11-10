package co.invest72.investment.presentation.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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
