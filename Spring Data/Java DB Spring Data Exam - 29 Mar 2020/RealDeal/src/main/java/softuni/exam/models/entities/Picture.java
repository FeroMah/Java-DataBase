package softuni.exam.models.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entities.baseId.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity(name = "pictures")
public class Picture extends BaseEntity {

    @Column(unique = true)
    @Length(min = 2, max = 20)
    private String name;

    @Column(name = "data_and_time")
    private LocalDateTime dateAndTime;

    @ManyToOne(targetEntity = Car.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;


    @ManyToMany(targetEntity = Offer.class, cascade = CascadeType.ALL)
    @JoinTable(name = "offers_pictures", joinColumns = @JoinColumn(name = "offer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "picture_id", referencedColumnName = "id"))
    private List<Offer> offers;



}
