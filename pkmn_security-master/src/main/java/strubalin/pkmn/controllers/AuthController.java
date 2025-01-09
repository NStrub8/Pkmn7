package strubalin.pkmn.controllers;

import strubalin.pkmn.models.LoginRequest;
import strubalin.pkmn.security.service.JwtService;
import strubalin.pkmn.security.service.LoginService;
import strubalin.pkmn.security.service.RegistrationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    private final LoginService loginService;

    private final RegistrationService registrationService;

    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws CredentialException {
        if (!jdbcUserDetailsManager.userExists(loginRequest.getUsername())) {
            return ResponseEntity.ok("User should be registered");
        }
        String jwt = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody LoginRequest loginRequest) {
        registrationService.registerUser(loginRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/success")
    public ResponseEntity<String> success(@AuthenticationPrincipal UserDetails user, HttpServletResponse response) throws IOException {
        log.info("Authentificated user {}", user.getUsername());
        String jwt = jwtService.createJwt(user.getUsername(), user.getAuthorities().iterator().next());
        log.info("Create jwt token for user {}", jwt);
        response.addCookie(new Cookie("jwt", Base64.getEncoder().encodeToString(jwt.getBytes(StandardCharsets.UTF_8))));
        ClassPathResource resource = new ClassPathResource("success.html");
        String success = new String(Files.readAllBytes(resource.getFile().toPath()));
        return ResponseEntity.ok()
                .body(success);
    }
}