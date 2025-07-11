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
	public int calMonthsToReach(TargetAmount targetAmount, InterestRate interestRate) {
		int initialCapital = 0; // 초기 자본금은 0으로 설정
		return calMonthsToReach(initialCapital, targetAmount, interestRate);
	}

	@Override
	public int calMonthsToReach(int initialCapital, TargetAmount targetAmount, InterestRate interestRate) {
		double monthlyRate = interestRate.getMonthlyRate();
		int months = 0;
		double balance = initialCapital;
		while (balance < targetAmount.getAmount()) {
			balance *= (1 + monthlyRate);
			balance += amount;
			months++;
		}
		return months;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public int calPrincipal(int months) {
		return amount * months;
	}

	@Override
	public int calInterest(TargetAmount targetAmount, InterestRate interestRate) {
		int months = calMonthsToReach(targetAmount, interestRate);
		return interestRate.calMonthlyInterest(amount) * months;
	}
}
