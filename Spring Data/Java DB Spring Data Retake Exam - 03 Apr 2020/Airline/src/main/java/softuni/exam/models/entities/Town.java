package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entities.baseId.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@NoArgsConstructor
@Getter
@Setter

@Entity (name = "towns")
public class Town extends BaseEntity {

    @Column (unique = true)
    @Size(min = 2)
    @NotNull
    private String name ;

    @Column
    @Min(value = 0)
    private Long population;

    @Column
    @Lob
    private String guide;
}
