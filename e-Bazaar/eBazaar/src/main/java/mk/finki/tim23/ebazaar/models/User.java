package mk.finki.tim23.ebazaar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "USERS")
public class User {

    @Id
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany
    private List<Post> posts;

    public User() {
    }

    public User(String username, String password, String email, String fullName, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.role = Role.USER;
        this.posts = new ArrayList<>();
    }
}
