package click.bitbank.api.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private static final String REQUEST_TOPIC = "request-topic";
    private static final String EXCEPTION_TOPIC = "exception-topic";
    private static final String RESPONSE_TOPIC = "response-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Kafka request-topic 메시지 보내기
     * @param serverRequest : 서버 요청
     * @param request : 발행될 Request
     */
    public void sendRequestTopic(ServerRequest serverRequest, String request) {

        String topicFormat = String.format("[%s] %s Request: %s", serverRequest.methodName(), serverRequest.path(), request);

        log.info(String.format("===== Producing Request message >>> %s =====", topicFormat));
        // topic에 message 발행
        kafkaTemplate.send(REQUEST_TOPIC, topicFormat);
    }

    /**
     * Kafka exception-topic 메시지 보내기
     * @param serverRequest : 서버 요청
     * @param exception : 발행될 Exception
     */
    public void sendExceptionTopic(ServerRequest serverRequest, String exception) {

        String topicFormat = String.format("[%s] %s Exception: %s", serverRequest.methodName(), serverRequest.path(), exception);

        log.info(String.format("===== Producing Exception message >>> %s =====", topicFormat));
        // topic에 message 발행
        kafkaTemplate.send(EXCEPTION_TOPIC, topicFormat);
    }

    /**
     * Kafka response-topic 메시지 보내기
     * @param serverRequest : 서버 요청
     * @param response : 발행될 Response
     */
    public void sendResponseTopic(ServerRequest serverRequest, String response) {

        String topicFormat = String.format("[%s] %s Response: %s", serverRequest.methodName(), serverRequest.path(), response);

        log.info(String.format("===== Producing Response message >>> %s =====", topicFormat));
        // topic에 message 발행
        kafkaTemplate.send(RESPONSE_TOPIC, topicFormat);
    }
}
