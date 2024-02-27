package sample.authentication.model.event;

import sample.authentication.annotations.KafkaPayload;

@KafkaPayload
public record UserIdEvent(Long id) {
}