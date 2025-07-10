package application.time;

import java.time.LocalDate;

public interface DateProvider {
	default LocalDate now() {
		return LocalDate.now();
	}
}
