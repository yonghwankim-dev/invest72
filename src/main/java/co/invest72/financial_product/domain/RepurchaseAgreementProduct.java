package co.invest72.financial_product.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("RP")
@Getter
@SuperBuilder(toBuilder = true)
public class RepurchaseAgreementProduct extends FinancialProduct {

	protected RepurchaseAgreementProduct() {

	}

	@Override
	public void update(FinancialProduct updatedProduct) {
		validateOnUpdate(updatedProduct);
		super.update(updatedProduct);
	}

	private void validateOnUpdate(FinancialProduct updatedProduct) {
		if (!(updatedProduct instanceof RepurchaseAgreementProduct)) {
			throw new IllegalArgumentException("업데이트된 상품은 RepurchaseAgreementProduct여야 합니다.");
		}
	}
}
