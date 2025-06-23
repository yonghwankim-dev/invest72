package domain.invest_amount;

/**
 * 예치 금액 관리하는 인터페이스
 */
public interface LumpSumInvestmentAmount extends InvestmentAmount{
	int getDepositAmount();

	int calCompoundInterest(double totalGrowthFactor);
}
