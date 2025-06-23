public interface InterestRate {
	double getAnnualRate();

	double getMonthlyRate();

	double getAnnualInterest(int amount);

	/**
	 * 투자 기간에 따른 총 성장 계수를 계산합니다.
	 * 총 성장 계수 = (1 + 월 이자율) ^ 투자 기간(개월)
	 * @param investPeriod 투자 기간
	 */
	double calTotalGrowthFactor(InvestPeriod investPeriod);
}
