package softuni.exam.models.dtos.car;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter

public class CasImportStringDATEdto {

    @Expose
    @Length(min = 2, max = 20)
    private String make;

    @Expose
    @Length(min = 2, max = 20)
    private String model;

    @Expose
    @Min(value = 0)
    private Integer kilometers;

    @Expose
    private String registeredOn;

//"04/04/2016" example
}
