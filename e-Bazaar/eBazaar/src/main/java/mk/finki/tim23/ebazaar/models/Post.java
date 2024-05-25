package mk.finki.tim23.ebazaar.models;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.tomcat.util.codec.binary.Base64;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double price;
    private LocalDateTime date;
    private String description;

    @Lob
    private byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Category category;
    @ManyToOne
    private User postAuthor;

    //long,lat
    private String location;
    private String city;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post() {
    }

    public Post(String title, Double price, String description,
                byte[] image, Category category, User postAuthor, String location, String city) {
        this.title = title;
        this.price = price;
        this.date = LocalDateTime.now();
        this.description = description;
        this.image = image;
        this.category = category;
        this.postAuthor = postAuthor;
        this.location = location;
        this.city = city;
    }

    public String generateBase64Image() {
        return Base64.encodeBase64String(this.image);
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}