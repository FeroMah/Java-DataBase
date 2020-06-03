package softuni.exam.models.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entities.baseId.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter

@Entity(name = "passengers")
public class Passenger extends BaseEntity {

    @Column(name = "first_name")
    @Size(min = 2)
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 2)
    private String lastName;

    @Column
    @Min(value = 0)
    private Integer age;


    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(unique = true)
    @Pattern(regexp = ".+@.+\\..+")
    private String email;


    @ManyToOne(targetEntity = Town.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "town_id", referencedColumnName = "id")
    private Town town;

    @OneToMany(mappedBy = "passenger")
    private List<Ticket> tickets;


}
