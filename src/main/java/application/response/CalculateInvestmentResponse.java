package application.response;

public class CalculateInvestmentResponse {
	private final int totalProfitAmount;
	private final int totalPrincipalAmount;
	private final int interest;

	public CalculateInvestmentResponse(int totalProfitAmount, int totalPrincipalAmount, int interest) {
		this.totalProfitAmount = totalProfitAmount;
		this.totalPrincipalAmount = totalPrincipalAmount;
		this.interest = interest;
	}

	public int getTotalProfitAmount() {
		return totalProfitAmount;
	}

	public int getTotalPrincipalAmount() {
		return totalPrincipalAmount;
	}

	public int getInterest() {
		return interest;
	}
}
