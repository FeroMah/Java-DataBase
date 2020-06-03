package softuni.exam.models.dtos.picture;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.models.entities.Car;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Setter

public class PictureSeedDTO {

    @Length(min = 2, max = 20)
    private String name;


    private LocalDateTime dateAndTime;


    private Car car;
}
