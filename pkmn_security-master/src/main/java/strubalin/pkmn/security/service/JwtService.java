package strubalin.pkmn.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    private final UserDetailsService userDetailsService;

    @Value("${token.secret}")
    private String SECRET_KEY;

    @Value("${token.expiration}")
    private long TOKEN_EXPIRATION_MINUTE;

    public String createJwt(String username, GrantedAuthority authority) {
        log.info("Creating JWT for user: {} with authority: {}", username, authority.getAuthority());

        return JWT.create()
                .withIssuer("pkmn")
                .withSubject(username)
                .withClaim("authority", authority.getAuthority())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(TOKEN_EXPIRATION_MINUTE * 60)))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public DecodedJWT verify(String jwt) {
        try {
            JWTVerifier verifier = JWT
                    .require(Algorithm.HMAC512(SECRET_KEY))
                    .withIssuer("pkmn")
                    .build();

            log.info("Verifying JWT: {}", jwt);

            DecodedJWT decodedJWT = verifier.verify(jwt);

            log.info("Decoded JWT subject: {}", decodedJWT.getSubject());

            if (Objects.isNull(userDetailsService.loadUserByUsername(decodedJWT.getSubject()))) {
                log.warn("User not found: {}", decodedJWT.getSubject());
                return null;
            }

            return decodedJWT;
        } catch (JWTVerificationException e) {
            log.error("JWT verification failed: {}", e.getMessage());
            return null;
        }
    }
}
