package click.bitbank.api.domain.model.alarm;

import click.bitbank.api.application.response.AlarmCountResponse;
import click.bitbank.api.application.response.AlarmListResponse;
import click.bitbank.api.domain.repository.AlarmRepository;
import click.bitbank.api.infrastructure.factory.AlarmResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AlarmFindSpecification {

    private final AlarmRepository alarmRepository;
    private final AlarmResponseFactory alarmResponseFactory;

    /**
     * 읽지 않은 알림 갯수 조회
     * @param memberId : 회원 고유번호
     * @return Mono<AlarmCountResponse> : 읽지 않은 알림 갯수
     */
    public Mono<AlarmCountResponse> unreadAlarmCountByMember(int memberId) {

        return alarmRepository.countByMemberIdAndAlarmCheck(memberId, false)
            .flatMap(alarmResponseFactory::alarmCountResponseBuilder);
    }

    /**
     * 읽지 않은 알림 목록 조회
     * @param memberId : 회원 고유번호
     * @return Mono<AlarmListResponse> : 읽지 않은 알림 목록
     */
    public Mono<AlarmListResponse> unreadAlarmListByMember(int memberId) {
        return alarmRepository.findByMemberIdAndAlarmCheck(memberId, false) // 읽지 않은 알림 목록 조회
            .flatMap(alarm -> {
                alarm.readAlarmCheck(); // 알림 읽음 처리

                return alarmRepository.save(alarm);
            })
            .collectList()
            .map(alarmResponseFactory::alarmListResponseBuilder)
            .switchIfEmpty(Mono.just(new AlarmListResponse()));
    }
}
