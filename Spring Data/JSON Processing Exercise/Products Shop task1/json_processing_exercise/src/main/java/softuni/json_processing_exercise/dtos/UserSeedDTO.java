package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@Setter
public class UserSeedDTO {

    @Expose
    private String firstName;

    @Expose
    @Length(min = 3, message = "User's last name is too short!")
    private String lastName;

    @Expose
    private Long age;
}
