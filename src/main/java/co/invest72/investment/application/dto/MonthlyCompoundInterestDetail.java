package co.invest72.investment.application.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MonthlyCompoundInterestDetail {
	private final int month;
	private final BigDecimal principal;
	private final BigDecimal interest;
	private final BigDecimal tax;
	private final BigDecimal profit;

	@Builder
	public MonthlyCompoundInterestDetail(int month, BigDecimal principal, BigDecimal interest, BigDecimal tax,
		BigDecimal profit) {
		this.month = month;
		this.principal = principal;
		this.interest = interest;
		this.tax = tax;
		this.profit = profit;
	}
}
