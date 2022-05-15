package click.bitbank.api.infrastructure.cron;

import click.bitbank.api.application.member.AlarmSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    private final AlarmSchedulerService alarmSchedulerService;

    @PostConstruct
    void init() {
        // 5분 마다 알림 생성
        Flux.interval(Duration.ofMinutes(5))
            .onBackpressureDrop()
            .flatMap(aLong -> {
                alarmSchedulerService.alarmAccountBookJob().subscribeOn(Schedulers.boundedElastic()).subscribe();
                alarmSchedulerService.alarmCardJob().subscribeOn(Schedulers.boundedElastic()).subscribe();
                return Mono.empty();
            })
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();
    }
}
