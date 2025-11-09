package co.invest72.investment.domain;

public interface TaxRate {
	int applyTo(int amount);

	double getRate();
}
