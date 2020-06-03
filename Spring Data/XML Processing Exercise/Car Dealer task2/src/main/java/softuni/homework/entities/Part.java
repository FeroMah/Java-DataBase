package softuni.homework.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.homework.entities.baseId.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "parts")
public class Part extends BaseEntity {
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private Long quantity;

    @ManyToOne(targetEntity = Supplier.class)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @ManyToMany(targetEntity = Car.class, cascade = CascadeType.ALL)
    @JoinTable(name = "parts_cars", joinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
    private List<Car> carsList;


}


