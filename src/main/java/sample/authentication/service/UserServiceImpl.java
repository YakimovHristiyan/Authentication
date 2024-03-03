package sample.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.authentication.constant.MessageConstants;
import sample.authentication.exception.BadRequestException;
import sample.authentication.exception.NotFoundException;
import sample.authentication.mapper.UserMapper;
import sample.authentication.model.entity.User;
import sample.authentication.model.event.UserIdEvent;
import sample.authentication.model.payload.request.RegistrationRequest;
import sample.authentication.model.payload.response.AuthDetailsResponse;
import sample.authentication.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final KafkaHandler kafkaHandler;

    @Override
    public AuthDetailsResponse register(RegistrationRequest userRegistrationRequest) {
        return this.userRepository
                .getIfFree(userRegistrationRequest.username(), userRegistrationRequest)
                .map(this.userMapper::map)
                .map(this.userRepository::save)
                .map(saved -> {
                    final var response = this.userMapper.map(saved);
                    this.publishUserDetails(saved, userRegistrationRequest);
                    this.publishUserId(saved.getId());
                    return response;
                })
                .orElseThrow(() -> new BadRequestException(MessageConstants.USER_EXISTS));
    }

    @Override
    public AuthDetailsResponse getById(Long id) {
        return this.userRepository.findById(id)
                .map(this.userMapper::map)
                .orElseThrow(() -> new NotFoundException(
                        String.format(MessageConstants.USER_NOT_FOUND, id))
                );
    }

    private void publishUserDetails(User user, RegistrationRequest userRegistrationRequest) {
        this.kafkaHandler.send(this.userMapper.map(user, userRegistrationRequest));
    }

    private void publishUserId(Long id) {
        this.kafkaHandler.send(new UserIdEvent(id));
    }
}