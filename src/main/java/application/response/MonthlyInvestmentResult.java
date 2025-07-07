package application.response;

import java.util.Objects;

public class MonthlyInvestmentResult {
	private final int month;
	private final int principal;
	private final int interest;
	private final int tax;
	private final int totalProfit;

	public MonthlyInvestmentResult(int month, int principal, int interest, int tax, int totalProfit) {
		this.month = month;
		this.principal = principal;
		this.interest = interest;
		this.tax = tax;
		this.totalProfit = totalProfit;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		MonthlyInvestmentResult that = (MonthlyInvestmentResult)object;
		return month == that.month && principal == that.principal && interest == that.interest && tax == that.tax
			&& totalProfit == that.totalProfit;
	}

	@Override
	public int hashCode() {
		return Objects.hash(month, principal, interest, tax, totalProfit);
	}
}
