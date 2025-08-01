package co.invest72.investment.application;

public class AddInvestment {

	private final InvestmentProductRepository repository;

	public AddInvestment(InvestmentProductRepository repository) {
		this.repository = repository;
	}

	public Long save(InvestmentProduct product) {
		InvestmentProduct saveProduct = repository.save(product);
		return saveProduct.getId();
	}
}
