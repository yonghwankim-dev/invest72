package domain.amount;

import domain.interest_rate.InterestRate;

public class MonthlyInvestmentAmount implements TargetAmountReachable {

	private final int amount;

	public MonthlyInvestmentAmount(int amount) {
		this.amount = amount;
		if (amount <= 0) {
			throw new IllegalArgumentException("월 투자 금액은 0보다 커야 합니다.");
		}
	}

	@Override
	public int calMonthsToReach(int targetAmount) {
		if (amount >= targetAmount) {
			return 1;
		}
		int months = targetAmount / amount;
		if (targetAmount % amount == 0) {
			return months;
		}
		return months + 1;
	}

	@Override
	public int calMonthsToReach(TargetAmount targetAmount, InterestRate interestRate) {
		double monthlyRate = interestRate.getMonthlyRate();
		int months = 0;
		double balance = 0;
		while (balance < targetAmount.getAmount()) {
			balance *= (1 + monthlyRate);
			balance += amount;
			months++;
		}
		months = months - 1;
		return months;
	}

	@Override
	public int getAmount() {
		return amount;
	}
}
