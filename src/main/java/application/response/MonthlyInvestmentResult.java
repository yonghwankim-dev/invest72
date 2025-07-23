package application.response;

public record MonthlyInvestmentResult(int month, int principal, int interest, int tax, int totalProfit) {

	@Override
	public String toString() {
		return "MonthlyInvestmentResult{" +
			"month=" + month +
			", principal=" + principal +
			", interest=" + interest +
			", tax=" + tax +
			", totalProfit=" + totalProfit +
			'}';
	}
}
