package co.invest72.investment.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;
import jakarta.validation.Valid;

@RestController
public class InvestmentRestController {

	private final CalculateExpirationInvestment calculateExpirationInvestment;
	private final CalculateMonthlyInvestment calculateMonthlyInvestment;

	public InvestmentRestController(CalculateExpirationInvestment calculateExpirationInvestment,
		CalculateMonthlyInvestment calculateMonthlyInvestment) {
		this.calculateExpirationInvestment = calculateExpirationInvestment;
		this.calculateMonthlyInvestment = calculateMonthlyInvestment;
	}

	@PostMapping("/investments/calculate/expiration")
	public ResponseEntity<CalculateExpirationInvestment.CalculateExpirationInvestmentResponse> calculateExpiration(
		@Valid @RequestBody CalculateInvestmentRequest request) {
		CalculateExpirationInvestment.CalculateExpirationInvestmentResponse response = calculateExpirationInvestment.calInvestment(
			request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/investments/calculate/monthly")
	public ResponseEntity<CalculateMonthlyInvestment.CalculateMonthlyInvestmentResponse> calculateMonthly(
		@Valid @RequestBody CalculateInvestmentRequest request) {
		CalculateMonthlyInvestment.CalculateMonthlyInvestmentResponse response = calculateMonthlyInvestment.calMonthlyInvestmentAmount(
			request);
		return ResponseEntity.ok(response);
	}

}
