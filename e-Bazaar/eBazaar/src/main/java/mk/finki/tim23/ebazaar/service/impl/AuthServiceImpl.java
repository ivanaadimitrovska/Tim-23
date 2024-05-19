package mk.finki.tim23.ebazaar.service.impl;

import mk.finki.tim23.ebazaar.models.User;
import mk.finki.tim23.ebazaar.models.exceptions.InvalidUserCredentialsException;
import mk.finki.tim23.ebazaar.models.exceptions.InvalidUsernameOrPasswordException;
import mk.finki.tim23.ebazaar.models.exceptions.UserNotFoundException;
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

    public User login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidUsernameOrPasswordException("Username or password is empty");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidUsernameOrPasswordException("Incorrect password");
        }

        return user;
    }
}
