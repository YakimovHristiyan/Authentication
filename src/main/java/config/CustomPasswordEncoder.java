package config;

import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder extends BCryptPasswordEncoder {

    @Named("encode")
    @Override
    public String encode(CharSequence password) {
        return super.encode(password);
    }
}