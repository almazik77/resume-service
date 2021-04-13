package a.a.resumeservice.services;

import a.a.resumeservice.dto.SignUpDto;
import a.a.resumeservice.dto.UserDto;
import a.a.resumeservice.mappers.UserMapper;
import a.a.resumeservice.model.User;
import a.a.resumeservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public void save(SignUpDto signUpDto) {
        if (userRepository.findUserByEmail(signUpDto.getEmail()).isPresent())
            throw new RuntimeException("User account already exists");

        String hashPassword = passwordEncoder.encode(signUpDto.getPassword());
        User user = User.builder()
                .email(signUpDto.getEmail())
                .firstName(signUpDto.getFirstName())
                .hashPassword(hashPassword)
                .lastName(signUpDto.getLastName())
                .build();
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDto getProfileInfo(String userId) {
        Optional<User> userCandidate = userRepository.findById(userId);
        if (userCandidate.isPresent())
            return userMapper.map(userCandidate.get());
        else throw new IllegalArgumentException("No user with id " + userId);
    }
}
