package mk.finki.tim23.ebazaar.service;

import mk.finki.tim23.ebazaar.models.User;


public interface AuthService {
    User login(String username, String password);
}
