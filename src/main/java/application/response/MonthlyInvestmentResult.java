package application.response;

public record MonthlyInvestmentResult(int month, int principal, int interest, int tax, int totalProfit) {
}
