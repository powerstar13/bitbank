package click.bitbank.api.infrastructure.factory;

import click.bitbank.api.application.response.AlarmCountResponse;
import click.bitbank.api.application.response.AlarmDTO;
import click.bitbank.api.application.response.AlarmListResponse;
import click.bitbank.api.domain.model.alarm.Alarm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlarmResponseFactory {

    private final ModelMapper modelMapper;

    /**
     * 읽지 않은 알림 갯수 결과 구성
     * @param aLong : 읽지 않은 알림 갯수
     * @return Mono<AlarmCountResponse> :읽지 않은 알림 갯수 결과
     */
    public Mono<AlarmCountResponse> alarmCountResponseBuilder(long aLong) {

        return Mono.just(new AlarmCountResponse(aLong));
    }

    /**
     * 읽지 않은 알림 목록 결과 구성
     * @param alarmList : 읽지 않은 알림 목록
     * @return Mono<AlarmCountResponse> :읽지 않은 알림 목록 결과
     */
    public AlarmListResponse alarmListResponseBuilder(List<Alarm> alarmList) {

        // 알림 메시지 목록
        List<AlarmDTO> alarmMessageList = alarmList.stream()
            .map(alarm -> modelMapper.map(alarm, AlarmDTO.class))
            .collect(Collectors.toList());

        return new AlarmListResponse(alarmMessageList);
    }
}
