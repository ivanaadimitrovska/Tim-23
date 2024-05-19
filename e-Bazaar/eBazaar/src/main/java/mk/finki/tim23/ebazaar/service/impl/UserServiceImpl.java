package mk.finki.tim23.ebazaar.service.impl;

import mk.finki.tim23.ebazaar.models.Post;
import mk.finki.tim23.ebazaar.models.Role;
import mk.finki.tim23.ebazaar.models.User;
import mk.finki.tim23.ebazaar.models.exceptions.InvalidUsernameOrPasswordException;
import mk.finki.tim23.ebazaar.models.exceptions.PasswordsDoNotMatchException;
import mk.finki.tim23.ebazaar.models.exceptions.UsernameAlreadyExistsException;
import mk.finki.tim23.ebazaar.repository.UserRepository;
import mk.finki.tim23.ebazaar.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String confirmPassowrd, String email, String fullName, String phoneNumber) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException("invalid username");
        if (!password.equals(confirmPassowrd))
            throw new PasswordsDoNotMatchException();
        if (this.userRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        User user = new User(username, passwordEncoder.encode(password), email, fullName, phoneNumber);
        return userRepository.save(user);
    }
}
