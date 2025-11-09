package co.invest72.investment.presentation.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Getter
public final class CalculateInvestmentRequest {
	private String type;
	private String amount;
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
			"amount=" + amount + ", " +
			"periodType=" + periodType + ", " +
			"periodValue=" + periodValue + ", " +
			"interestType=" + interestType + ", " +
			"annualInterestRate=" + annualInterestRate + ", " +
			"taxType=" + taxType + ", " +
			"taxRate=" + taxRate + ']';
	}
}
