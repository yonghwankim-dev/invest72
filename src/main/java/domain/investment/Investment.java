package domain.investment;

// todo: month를 입력받아서 특정 회차까지 투자 금액을 계산하는 메서드 추가, ex) getPrincipal(int month)
public interface Investment {
	int getAmount();

	int getPrincipal();

	int getPrincipal(int month);

	int getInterest();

	int getTax();
}
