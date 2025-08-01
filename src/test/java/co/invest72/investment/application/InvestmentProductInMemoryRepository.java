package co.invest72.investment.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

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
}
