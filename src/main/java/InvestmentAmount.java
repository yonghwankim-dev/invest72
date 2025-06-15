public interface InvestmentAmount {
	int getMonthlyAmount();

	/**
	 * 연간 수익 금액을 계산하여 반환합니다.
	 * 예시: 월 10만 원을 투자하고 이율이 5%인 경우, 연간 수익 금액은 12만 원입니다.
	 * @param interestRate 이율
	 * @return 연간 수익 금액
	 */
	double getAnnualInterest(InterestRate interestRate);
}
