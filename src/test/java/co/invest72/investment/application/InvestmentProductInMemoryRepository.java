package co.invest72.investment.application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import co.invest72.investment.domain.Investment;

public class InvestmentProductInMemoryRepository implements InvestmentProductRepository {

	private static final AtomicLong SEQUENCE = new AtomicLong(1L);
	private final List<InvestmentProduct> store = new ArrayList<>();

	@Override
	public void save(String uid, Investment investment) {
		long id = SEQUENCE.getAndIncrement();
		InvestmentProduct product = new InvestmentProduct(id, uid, investment);
		store.add(product);
	}
}
