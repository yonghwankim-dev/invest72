package co.invest72.product.domain;

import java.util.List;
import java.util.Optional;

public interface InvestmentProductRepository {

	InvestmentProduct save(InvestmentProduct product);

	Optional<InvestmentProduct> findById(Long id);

	List<InvestmentProduct> findAllByUid(String uid);
}
