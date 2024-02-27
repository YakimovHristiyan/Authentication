package sample.authentication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sample.authentication.constants.MessageConstants;
import sample.authentication.exception.NotFoundException;
import sample.authentication.repository.UserRepository;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(MessageConstants.USER_NOT_FOUND));
        var authorities = new ArrayList<SimpleGrantedAuthority>();

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}