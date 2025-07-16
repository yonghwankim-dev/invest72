package application.request;

public record TargetAchievementRequest(int initialCapital, int targetAmount,
									   int monthlyInvestmentAmount, double interestRate,
									   String taxType, double taxRate) {

	public TargetAchievementRequest(int targetAmount, int monthlyInvestmentAmount,
		double interestRate, String taxType, double taxRate) {
		this(0, targetAmount, monthlyInvestmentAmount, interestRate, taxType, taxRate);
	}
}
