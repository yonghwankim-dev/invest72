public interface InterestRate {
	double getAnnualRate();

	double getMonthlyRate();

	double getAnnualInterest(int amount);

	/**
	 * 월 이자율을 적용한 성장 계수를 반환합니다.
	 * 성장 계수 = 1 + 월 이자율
	 */
	double getGrowthFactor();
}
