package domain.tax;

public interface TaxRate {
	int applyTo(int amount);

	double getRate();
}
