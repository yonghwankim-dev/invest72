package co.invest72.investment.application;

import java.util.Optional;

public interface InvestmentProductRepository {

	InvestmentProduct save(InvestmentProduct product);

	Optional<InvestmentProduct> findById(Long id);
}
