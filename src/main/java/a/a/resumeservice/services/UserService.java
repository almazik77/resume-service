package a.a.resumeservice.services;

import a.a.resumeservice.dto.SignUpDto;
import a.a.resumeservice.dto.UserDto;

public interface UserService {
    void save(SignUpDto signUpDto);

    UserDto getProfileInfo(String userId);
}
