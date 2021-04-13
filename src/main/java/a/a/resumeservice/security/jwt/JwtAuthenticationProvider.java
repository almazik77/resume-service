package a.a.resumeservice.security.jwt;

import a.a.resumeservice.model.User;
import a.a.resumeservice.repositories.UserRepository;
import a.a.resumeservice.security.details.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Profile("rest")
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;
    @Value(value = "${jwt.secret}")
    private String secret;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();

        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            Optional<User> userCandidate = userRepository.findById(claims.get("sub", String.class));
            if (userCandidate.isPresent()) {
                UserDetails userDetails = new UserDetailsImpl(userCandidate.get());
                authentication.setAuthenticated(true);
                ((JwtAuthentication) authentication).setUserDetails(userDetails);
            }
            return authentication;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Bad token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}