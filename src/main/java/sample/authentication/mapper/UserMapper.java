package sample.authentication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import sample.authentication.config.CustomPasswordEncoder;
import sample.authentication.model.entity.User;
import sample.authentication.model.event.RegistrationEvent;
import sample.authentication.model.payload.request.RegistrationRequest;
import sample.authentication.model.payload.response.AuthDetailsResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = CustomPasswordEncoder.class)
public interface UserMapper {

    @Mapping(target = "password", qualifiedByName = "encode")
    User map(RegistrationRequest registrationRequest);

    AuthDetailsResponse map(User user);

    RegistrationEvent map(User user, RegistrationRequest registrationRequest);
}