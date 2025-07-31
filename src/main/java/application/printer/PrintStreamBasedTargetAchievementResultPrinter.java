package application.printer;

import java.io.IOException;
import java.io.PrintStream;

import co.invest72.achievement.application.CalculateAchievement;

public class PrintStreamBasedTargetAchievementResultPrinter implements TargetAchievementResultPrinter {

	private final PrintStream out;

	public PrintStreamBasedTargetAchievementResultPrinter(PrintStream out) {
		this.out = out;
	}

	@Override
	public void printResult(CalculateAchievement.AchievementResponse response) {
		out.println("목표 달성 날짜: " + response.achievedDate());
		out.println("원금: " + formatAmount(response.principal()));
		out.println("이자: " + formatAmount(response.interest()));
		out.println("세금: " + formatAmount(response.tax()));
		out.println("세후 이자: " + formatAmount(response.afterTaxInterest()));
		out.println("총 수익: " + formatAmount(response.totalProfit()));
	}

	private String formatAmount(int amount) {
		return String.format("%,d원", amount);
	}

	@Override
	public void printError(IOException e) {
		out.println("[ERROR] 입력 오류: " + e.getMessage());
	}
}
