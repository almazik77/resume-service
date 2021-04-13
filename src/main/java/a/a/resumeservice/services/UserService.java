package a.a.resumeservice.services;

import a.a.resumeservice.dto.SignInDto;
import a.a.resumeservice.dto.SignUpDto;
import a.a.resumeservice.dto.TokenDto;
import a.a.resumeservice.dto.UserDto;

import java.util.Optional;

public interface UserService {
    void save(SignUpDto signUpDto);

    UserDto getProfileInfo(String userId);

    Optional<TokenDto> signIn(SignInDto signInDto);
}
