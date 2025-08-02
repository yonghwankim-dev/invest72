package co.invest72.product.application;

import java.util.List;

import co.invest72.product.domain.InvestmentProductRepository;

public class GetInvestmentProducts {

	private final InvestmentProductRepository repository;

	public GetInvestmentProducts(InvestmentProductRepository repository) {
		this.repository = repository;
	}

	public List<InvestmentProductDto> listInvestment(String uid) {
		return repository.findAllByUid(uid).stream()
			.map(InvestmentProductDto::from)
			.toList();
	}
}
