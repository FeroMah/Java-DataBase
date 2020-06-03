package softuni.homework.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.homework.entities.baseId.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter

@Table(name = "customers")
public class Customer extends BaseEntity {
    @Column
    private String name ;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "is_young_driver")
    private Boolean isYoungDriver;

    @OneToMany(mappedBy = "customer")
    private List<Sale> saleList;
}
