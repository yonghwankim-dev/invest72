/**
 * 이자 계산 방식에 따라서 총 투자 금액 계산하는 역할
 */
public interface Interest {
	int getTotalPrincipal();
	int getInterestAmount();
	int getTax();
	int getAmount();
}
