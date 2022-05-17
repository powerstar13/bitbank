package click.bitbank.api.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    /**
     * 강의 오픈 토픽 구독
     * @param message : 받은 메시지
     */
//    @KafkaListener(id = "member", topics = "lecture-open-topic", groupId = "member-group")
    public void lectureOpenConsume(String message) {
        log.info(String.format("===== Consumed message >>> %s =====", message));
    }
}
