package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.constats.Rating;
import softuni.exam.models.entities.baseId.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity(name = "sellers")
public class Seller extends BaseEntity {

    @Column(unique = true)
    @Pattern(regexp = ".+@.+\\..+")
    private String email;

    @Column(name = "first_name")
    @Length(min = 2, max = 20)
    private String firstName;

    @Column(name = "last_name")
    @Length(min = 2, max = 20)
    private String lastName;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private Rating rating;

    @Column
    private String town;

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    private List<Offer> offers;


}
