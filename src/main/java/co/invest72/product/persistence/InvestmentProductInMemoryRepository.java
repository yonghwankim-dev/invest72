package co.invest72.product.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import co.invest72.product.domain.InvestmentProduct;
import co.invest72.product.domain.InvestmentProductRepository;

public class InvestmentProductInMemoryRepository implements InvestmentProductRepository {

	private static final AtomicLong SEQUENCE = new AtomicLong(1L);
	private final List<InvestmentProduct> store = new ArrayList<>();

	@Override
	public InvestmentProduct save(InvestmentProduct product) {
		long id = SEQUENCE.getAndIncrement();
		product.setId(id);
		store.add(product);
		return product;
	}

	@Override
	public Optional<InvestmentProduct> findById(Long id) {
		return store.stream()
			.filter(product -> product.getId().equals(id))
			.findFirst();
	}

	@Override
	public List<InvestmentProduct> findAllByUid(String uid) {
		return store.stream()
			.filter(product -> product.getUid().equals(uid))
			.toList();
	}
}
