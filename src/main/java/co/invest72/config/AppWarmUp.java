package co.invest72.config;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import co.invest72.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppWarmUp implements ApplicationListener<ApplicationReadyEvent> {

	private final UserRepository userRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		triggerWarmUp("최초 서버 구동");
	}

	@Scheduled(fixedRate = 30, initialDelay = 30, timeUnit = TimeUnit.MINUTES)
	public void periodicWarmUp() {
		triggerWarmUp("주기적 헬스 체크 및 스케줄");
	}

	private void triggerWarmUp(String source) {
		try {
			long startTime = System.currentTimeMillis();

			userRepository.findById("dummyId");

			long duration = System.currentTimeMillis() - startTime;
			log.info("[{}] 웜업 및 서킷 유지 완료! (소요 시간: {}ms)", source, duration);
		} catch (Exception e) {
			log.error("[{}] 웜업 중 에러 발생: ", source, e);
		}
	}
}
