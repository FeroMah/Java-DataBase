package softuni.exam.models.dtos.passenger;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter

public class PassengerSeedDTO {

    @Expose
    @Size(min = 2)
    private String firstName;

    @Expose
    @Size(min = 2)
    @NotNull
    private String lastName;

    @Expose
    @Min(value = 0)
    private Integer age;


    @Expose
    private String phoneNumber;

    @Expose
    @Pattern(regexp = ".+@.+\\..+")
    @NotNull
    private String email;

    @Expose
    private String town;
}
