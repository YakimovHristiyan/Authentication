package sample.authentication.model.payload.response;

import java.util.List;

public record ErrorResponse(List<FieldError> errors) {
}