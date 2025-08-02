package co.invest72.product.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import co.invest72.product.domain.InvestmentProductEntity;
import co.invest72.product.domain.InvestmentProductRepository;

public class InvestmentProductInMemoryRepository implements InvestmentProductRepository {

	private static final AtomicLong SEQUENCE = new AtomicLong(1L);
	private final List<InvestmentProductEntity> store = new ArrayList<>();

	@Override
	public InvestmentProductEntity save(InvestmentProductEntity product) {
		long id = SEQUENCE.getAndIncrement();
		product.setId(id);
		store.add(product);
		return product;
	}

	@Override
	public Optional<InvestmentProductEntity> findById(Long id) {
		return store.stream()
			.filter(product -> product.getId().equals(id))
			.findFirst();
	}

	@Override
	public List<InvestmentProductEntity> findAllByUid(String uid) {
		return store.stream()
			.filter(product -> product.getUid().equals(uid))
			.toList();
	}
}
