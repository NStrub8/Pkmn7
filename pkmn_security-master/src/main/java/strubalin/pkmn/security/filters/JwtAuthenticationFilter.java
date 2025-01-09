package strubalin.pkmn.security.filters;


import strubalin.pkmn.security.service.JwtService;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = extractJwtToken(request);

        if (Objects.isNull(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        DecodedJWT decodedJWT = jwtService.verify(jwtToken);

        if (Objects.isNull(decodedJWT)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthentication(decodedJWT);
        filterChain.doFilter(request, response);
    }

    private String extractJwtToken(HttpServletRequest request) {
        // Check Authorization header
        String jwtToken = Optional.ofNullable(request.getHeader("Authorization"))
                .filter(token -> token.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);

        if (!Objects.isNull(jwtToken)) {
            log.info("JWT token from Authorization header: {}", jwtToken);
            return jwtToken;
        }

        // Check cookies if Authorization header is absent
        if (Objects.nonNull(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    jwtToken = new String(Base64.getDecoder().decode(cookie.getValue()));
                    log.info("JWT token from cookies: {}", jwtToken);
                    return jwtToken;
                }
            }
        }

        return null;
    }

    private void setAuthentication(DecodedJWT decodedJWT) {
        String authority = decodedJWT.getClaim("authority").asString();
        log.info("Token authority information: {}", authority);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                decodedJWT.getSubject(),
                null,
                List.of(new SimpleGrantedAuthority(authority))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}