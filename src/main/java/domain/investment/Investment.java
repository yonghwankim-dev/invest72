package domain.investment;

public interface Investment {
	int getTotalProfit();

	int getPrincipal();

	int getPrincipal(int month);

	int getInterest();

	int getTax();
}
