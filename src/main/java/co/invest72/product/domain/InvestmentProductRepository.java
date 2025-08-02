package co.invest72.product.domain;

import java.util.List;
import java.util.Optional;

public interface InvestmentProductRepository {

	InvestmentProductEntity save(InvestmentProductEntity product);

	Optional<InvestmentProductEntity> findById(Long id);

	List<InvestmentProductEntity> findAllByUid(String uid);
}
