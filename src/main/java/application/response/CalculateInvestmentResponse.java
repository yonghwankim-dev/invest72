package application.response;

public class CalculateInvestmentResponse {
	private final int totalProfitAmount;
	private final int totalPrincipalAmount;
	private final int interest;
	private final int tax;

	public CalculateInvestmentResponse(int totalProfitAmount, int totalPrincipalAmount, int interest, int tax) {
		this.totalProfitAmount = totalProfitAmount;
		this.totalPrincipalAmount = totalPrincipalAmount;
		this.interest = interest;
		this.tax = tax;
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

	public int getTax() {
		return tax;
	}
}
