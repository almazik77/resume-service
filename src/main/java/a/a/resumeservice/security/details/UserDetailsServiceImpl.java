package a.a.resumeservice.security.details;

import a.a.resumeservice.model.User;
import a.a.resumeservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userCandidate = userRepository.findUserByEmail(email);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            return new UserDetailsImpl(user);
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
