package application.response;

public class CalculateInvestmentResponse {
	private final int totalProfitAmount;

	public CalculateInvestmentResponse(int totalProfitAmount) {
		this.totalProfitAmount = totalProfitAmount;
	}

	public int getTotalProfitAmount() {
		return totalProfitAmount;
	}
}
