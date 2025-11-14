package co.invest72.investment.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.application.CalculateGoalInvestment;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.application.dto.CalculateGoalDto;
import co.invest72.investment.application.dto.CalculateGoalResultDto;
import co.invest72.investment.presentation.request.CalculateGoalRequest;
import co.invest72.investment.presentation.request.CalculateInvestmentRequest;
import co.invest72.investment.presentation.response.CalculateGoalInvestmentResponse;
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

	@PostMapping("/investments/calculate/goal")
	public ResponseEntity<CalculateGoalInvestmentResponse> calculateGoal(
		@Valid @RequestBody CalculateGoalRequest request) {
		CalculateGoalDto dto = CalculateGoalDto.builder()
			.monthlyInvestmentAmount(request.getMonthlyInvestmentAmount())
			.annualInterestRate(request.getAnnualInterestRate())
			.goalAmount(request.getGoalAmount())
			.startDate(request.getStartDate())
			.build();

		// 필요 개월수 및 달성일자 계산
		CalculateGoalResultDto resultDto = calculateGoalInvestment.calculate(dto);

		// todo: 월별 수익표 계산
		List<Object> details = new ArrayList<>();

		// response 생성
		CalculateGoalInvestmentResponse response = new CalculateGoalInvestmentResponse(
			resultDto.getMonths(),
			resultDto.getAchievedDate(),
			details
		);
		return ResponseEntity.ok(response);
	}
}
