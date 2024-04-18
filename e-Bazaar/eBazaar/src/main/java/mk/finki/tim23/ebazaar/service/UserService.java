package mk.finki.tim23.ebazaar.service;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import mk.finki.tim23.ebazaar.models.Role;
import mk.finki.tim23.ebazaar.models.User;

public interface UserService {

    User register(String username, String password, String confirmPassowrd, String email, String fullName);
}
