package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entities.baseId.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

@Entity(name = "tickets")
public class Ticket extends BaseEntity {

    @Column(name = "serial_number", unique = true)
    @Size(min = 2)
    @NotNull
    private String serialNumber;


    @Column
    @Min(value = 0)
    @NotNull
    private BigDecimal price;

    @Column
    @NotNull
//    TODO xml or Json
    private LocalDateTime takeoff;

    @ManyToOne(targetEntity = Plane.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "plane_id", referencedColumnName = "id")
    @NotNull
    private Plane plane;

    @ManyToOne(targetEntity = Passenger.class)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    @NotNull
    private Passenger passenger;

    @ManyToOne(targetEntity = Town.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "to_town_id", referencedColumnName = "id")
    @NotNull
    private Town toTown;

    @ManyToOne(targetEntity = Town.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "from_town_id", referencedColumnName = "id")
    @NotNull
    private Town fromTown;
}
