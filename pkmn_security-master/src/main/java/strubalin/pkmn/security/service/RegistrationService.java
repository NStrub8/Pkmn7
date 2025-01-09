package strubalin.pkmn.security.service;

import strubalin.pkmn.models.LoginRequest;
import strubalin.pkmn.models.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    private final PasswordEncoder passwordEncoder;

    public void registerUser(LoginRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserDTO userDetails = UserDTO.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .enabled(true)
                .build();

        jdbcUserDetailsManager.createUser(userDetails);
    }
}
