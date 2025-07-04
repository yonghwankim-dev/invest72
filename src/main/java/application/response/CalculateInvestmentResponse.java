package application.response;

public class CalculateInvestmentResponse {
	private final int totalProfitAmount;
	private final int totalPrincipalAmount;

	public CalculateInvestmentResponse(int totalProfitAmount, int totalPrincipalAmount) {
		this.totalProfitAmount = totalProfitAmount;
		this.totalPrincipalAmount = totalPrincipalAmount;
	}

	public int getTotalProfitAmount() {
		return totalProfitAmount;
	}

	public int getTotalPrincipalAmount() {
		return totalPrincipalAmount;
	}
}
