package softuni.exam.models.dtos.car;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.adaptors.LocalDateDeserializer;
import softuni.exam.adaptors.LocalDateSerializer;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter

public class CarSeedDTO {

    @Length(min = 2, max = 20)
    private String make;


    @Length(min = 2, max = 20)
    private String model;


    @Min(value = 0)
    private Integer kilometers;


    private LocalDate registeredOn;
//"04/04/2016" example

}
