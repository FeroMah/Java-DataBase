package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entities.baseId.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity(name = "cars")
public class Car extends BaseEntity {
    @Column
    @Min(value = 0)
    private Integer kilometers;

    @Column
    @Length(min = 2, max = 20)
    private String make;

    @Column
    @Length(min = 2, max = 20)
    private String model;

    @Column(name = "registration_on")
    private LocalDate registeredOn;


    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL)
    private List<Offer> offers;


    @OneToMany(mappedBy = "car",cascade = CascadeType.ALL)
    private List<Picture> pictures;


}
