package sample.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class KafkaHandlerImpl implements KafkaHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(Object clazz) {
        final var topic = TopicNamingService.getTopicName(clazz.getClass());
        this.kafkaTemplate.send(topic, clazz);
    }
}