package softuni.homework.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.homework.entities.baseId.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cars")
public class Car extends BaseEntity {
    @Column
    private String make;

    @Column
    private String model;

    @Column(name = "travelled_distance")
    private Long travelledDistance;

    @ManyToMany(mappedBy = "carsList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Part> partsList;
}
