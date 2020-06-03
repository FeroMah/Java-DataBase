package softuni.homework.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.homework.entities.baseId.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sales")
public class Sale extends BaseEntity {
    @Column
    private double discount;


    // adding relational column(f. key)
    @OneToOne(targetEntity = Car.class)
    @JoinColumn (name="car_id",referencedColumnName = "id")
    private Car car;

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn (name="customer_id",referencedColumnName = "id")
    private Customer customer;

}
