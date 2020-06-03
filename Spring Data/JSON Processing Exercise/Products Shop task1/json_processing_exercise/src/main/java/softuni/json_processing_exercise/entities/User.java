package softuni.json_processing_exercise.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter


public class User extends BaseEntity {

    private long age;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    @Length(min = 3, message = "User's last name is too short!")
    private String lastName;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> sold = new HashSet<Product>();

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private Set<Product> bought = new HashSet<Product>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id", referencedColumnName = "id"))
    private Set<User> friends = new HashSet<>();
}
