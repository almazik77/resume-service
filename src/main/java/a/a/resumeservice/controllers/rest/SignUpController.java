package a.a.resumeservice.controllers.rest;

import a.a.resumeservice.dto.SignUpDto;
import a.a.resumeservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("Rest")
@RequiredArgsConstructor
@RequestMapping("/rest/signUp")
public class SignUpController {
    private final UserService userService;

    @PreAuthorize("isAnonymous()")
    @PostMapping()
    public void signUp(@RequestBody SignUpDto signUpDto) {
        userService.save(signUpDto);
    }
}
