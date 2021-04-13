package a.a.resumeservice.services;

import a.a.resumeservice.dto.SignInDto;
import a.a.resumeservice.dto.SignUpDto;
import a.a.resumeservice.dto.TokenDto;
import a.a.resumeservice.dto.UserDto;
import a.a.resumeservice.mappers.UserMapper;
import a.a.resumeservice.model.User;
import a.a.resumeservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Value("${jwt.secret}")
    private String secret;

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

    @Override
    public Optional<TokenDto> signIn(SignInDto signInDto) {

        Optional<User> userCandidate = userRepository.findUserByEmail(signInDto.getEmail());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (passwordEncoder.matches(signInDto.getPassword(), user.getHashPassword())) {
                String tokenValue = Jwts.builder()
                        .setSubject(user.getId())
                        .claim("email", user.getEmail())
                        .signWith(SignatureAlgorithm.HS256, secret)
                        .compact();
                return Optional.ofNullable(TokenDto.builder().token(tokenValue).build());
            }
        }
        return Optional.empty();
    }
}
