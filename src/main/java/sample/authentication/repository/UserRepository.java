package sample.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.authentication.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    default <T> Optional<T> getIfFree(String username, T newUser) {
        return this.findByUsername(username)
                .or(() -> Optional.of(new User()))
                .filter(user -> user.getId() == null)
                .map(user -> newUser);
    }
}