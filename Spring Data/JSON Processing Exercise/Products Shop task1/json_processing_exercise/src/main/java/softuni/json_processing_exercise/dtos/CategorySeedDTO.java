package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@Getter
@Setter
public class CategorySeedDTO {
    @Expose
    @Length(min = 3, max = 15, message = "Category's name must be between 3 and 15 symbols max!")
    private String name;

}
