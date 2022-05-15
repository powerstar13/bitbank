package click.bitbank.api.application.member;

import click.bitbank.api.domain.model.alarm.Alarm;
import click.bitbank.api.domain.repository.AlarmRepository;
import click.bitbank.api.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmSchedulerService {

    private final MemberRepository memberRepository;
    private final AlarmRepository alarmRepository;

    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> alarmAccountBookJob() { return this.alarmMessageRegistration("오늘의 가계부를 작성을 해주세요!");}

    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> alarmCardJob() { return this.alarmMessageRegistration("소비 패턴에 따른 카드 추천을 받아보세요!");}

    private Mono<Void> alarmMessageRegistration(String message) {
        // 회원 전체 조회
        return memberRepository.findAllByDelDateIsNull()
            .flatMap(member ->
                // 읽지 않은 알림 메시지 갯수 조회
                alarmRepository.countByMemberIdAndAlarmCheck(member.getMemberId(), false)
                    .flatMap(aLong -> {
                        // 읽지 않은 알림 메시지가 없을 경우
                        if (aLong == 0L) {
                            return alarmRepository.save(
                                Alarm.builder()
                                    .memberId(member.getMemberId())
                                    .alarmMessage(member.getMemberName() + "님! " + message)
                                    .alarmCheck(false)
                                    .build()
                            );
                        }
                        return Mono.empty();
                    })
            )
            .thenEmpty(s -> log.info(s.toString()));
    }
}
