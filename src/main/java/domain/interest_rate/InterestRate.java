package domain.interest_rate;

import java.math.BigDecimal;

import domain.invest_period.InvestPeriod;

public interface InterestRate {
	double getAnnualRate();

	/**
	 * 월 이자율을 반환합니다.
	 * <p>
	 * 월 이자율 = 연이자율 / 12
	 * </p>
	 * @return 월 이자율
	 */
	BigDecimal getMonthlyRate();

	double getAnnualInterest(int amount);

	/**
	 * 투자 기간에 따른 총 성장 계수를 계산합니다.
	 * 총 성장 계수 = (1 + 월 이자율) ^ 투자 기간(개월)
	 * @param investPeriod 투자 기간
	 */
	double calTotalGrowthFactor(InvestPeriod investPeriod);

	/**
	 * 월 회차에 해당하는 성장 계수를 계산합니다.
	 * @param month 월 회차 (1부터 시작)
	 * @return 성장 계수
	 */
	double calGrowthFactor(int month);

	BigDecimal calMonthlyInterest(int amount);
}
