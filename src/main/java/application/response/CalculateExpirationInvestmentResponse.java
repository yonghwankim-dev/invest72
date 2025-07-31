package application.response;

public record CalculateExpirationInvestmentResponse(int totalProfitAmount, int totalPrincipalAmount, int interest,
													int tax) {

}
