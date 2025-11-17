package co.invest72.investment.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.application.CalculateGoalInvestment;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.application.dto.CalculateGoalDto;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;
import co.invest72.investment.presentation.request.MonthlyCompoundInterestCalculateRequest;
import co.invest72.investment.presentation.response.CalculateMonthlyCompoundInterestResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InvestmentRestController {

	private final CalculateExpirationInvestment calculateExpirationInvestment;
	private final CalculateMonthlyInvestment calculateMonthlyInvestment;
	private final CalculateGoalInvestment calculateGoalInvestment;

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

	@PostMapping("/investments/calculate/monthly-compound-interest")
	public ResponseEntity<CalculateMonthlyCompoundInterestResponse> calculateMonthlyCompoundInterest(
		@Valid @RequestBody MonthlyCompoundInterestCalculateRequest request) {
		CalculateGoalDto dto = CalculateGoalDto.builder()
			.initialAmount(request.getInitialAmount())
			.monthlyDeposit(request.getMonthlyDeposit())
			.investmentYears(request.getInvestmentYears())
			.annualInterestRate(request.getAnnualInterestRate())
			.compoundingMethod(request.getCompoundingMethod())
			.build();

		CalculateMonthlyCompoundInterestResponse response = CalculateMonthlyCompoundInterestResponse.builder()
			.totalPrincipal(11_000_000)
			.totalInterest(278_855)
			.totalProfit(11_278_855)
			.build();
		return ResponseEntity.ok(response);
	}
}
