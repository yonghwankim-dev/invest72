package co.invest72.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
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
		try {
			// 1. DB 커넥션 풀 강제 활성화 및 로딩
			userRepository.findById("dummyId");
			log.info("🔥 애플리케이션 웜업 완료.");
		} catch (Exception e) {
			// 웜업 실패 시 로그만 남기고 서버 구동은 유지
			log.error("웜업 중 에러 발생: ", e);
		}
	}
}
