package sample.authentication.model.event;

import sample.authentication.annotations.KafkaPayload;

import java.time.LocalDate;
@KafkaPayload
public record RegistrationEvent(Long id, String firstName, String lastName, LocalDate bornOn) {
}