package domain.investment;

public interface Investment {
	int getPrincipal();

	int getPrincipal(int month);

	int getInterest();

	int getInterest(int month);

	int getTax();

	int getTax(int month);

	int getTotalProfit();

	int getTotalProfit(int month);
}
