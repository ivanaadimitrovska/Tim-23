package mk.finki.tim23.ebazaar.service.impl;

import mk.finki.tim23.ebazaar.models.User;
import mk.finki.tim23.ebazaar.models.exceptions.InvalidUserCredentialsException;
import mk.finki.tim23.ebazaar.models.exceptions.InvalidUsernameOrPasswordException;
import mk.finki.tim23.ebazaar.repository.UserRepository;
import mk.finki.tim23.ebazaar.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidUsernameOrPasswordException();
        }
        User user = userRepository.findByUsername(username).orElseThrow();
        return passwordEncoder.matches(password, user.getPassword()) ? user : null;
    }
}
