package click.bitbank.api.domain.repository;

import click.bitbank.api.domain.model.alarm.Alarm;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AlarmRepository extends ReactiveCrudRepository<Alarm, Integer> {

    Mono<Long> countByMemberIdAndAlarmCheck(int memberId, boolean alarmCheck);

    Flux<Alarm> findByMemberIdAndAlarmCheck(int memberId, boolean alarmCheck);
}
