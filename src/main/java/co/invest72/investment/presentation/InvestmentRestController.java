package co.invest72.investment.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;
import jakarta.validation.Valid;

@RestController
public class InvestmentRestController {

	private final CalculateExpirationInvestment calculateExpirationInvestment;

	public InvestmentRestController(CalculateExpirationInvestment calculateExpirationInvestment) {
		this.calculateExpirationInvestment = calculateExpirationInvestment;
	}

	@PostMapping("/investments/calculate/expiration")
	public ResponseEntity<CalculateExpirationInvestment.CalculateExpirationInvestmentResponse> calculateExpiration(
		@Valid @RequestBody CalculateInvestmentRequest request) {
		CalculateExpirationInvestment.CalculateExpirationInvestmentResponse response = calculateExpirationInvestment.calInvestment(
			request);
		return ResponseEntity.ok(response);
	}
}
