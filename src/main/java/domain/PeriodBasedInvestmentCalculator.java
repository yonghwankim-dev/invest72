package domain;

import domain.investment.Investment;

/**
 * 기간 기반 투자 계산기 인터페이스
 * <p>
 * 이 인터페이스는 구현체에 따라서 기간 기반 투자 계산을 수행합니다.
 * 예를 들어, 만기 투자 또는 특정 월회차까지에 대한 원금 계산을 수행할 수 있습니다.
 */
public interface PeriodBasedInvestmentCalculator {
	int calPrincipal(Investment investment);
}
