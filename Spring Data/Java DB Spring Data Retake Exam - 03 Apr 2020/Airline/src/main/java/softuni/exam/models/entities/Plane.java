package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entities.baseId.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter


@Entity(name = "planes")
public class Plane extends BaseEntity {


    @Column(name = "register_number", unique = true)
    @Size(min = 5)
    @NotNull
    private String registerNumber;


    @Column
    @Min(value = 0)
    @NotNull
    private Integer capacity;

    @Column
    @Size(min = 2)
    @NotNull
    private String airline;
}
