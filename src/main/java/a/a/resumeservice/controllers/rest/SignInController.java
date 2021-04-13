package a.a.resumeservice.controllers.rest;

import a.a.resumeservice.dto.SignInDto;
import a.a.resumeservice.dto.TokenDto;
import a.a.resumeservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Profile("rest")
@RequiredArgsConstructor
public class SignInController {
    private final UserService userService;

    @PreAuthorize("permitAll()")
    @PostMapping("/rest/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInDto signInDto) {
        Optional<TokenDto> tokenDtoCandidate = userService.signIn(signInDto);
        return tokenDtoCandidate.isPresent() ?
                ResponseEntity.ok(tokenDtoCandidate.get()) :
                ResponseEntity.badRequest().body("Wrong login or password");
    }
}
