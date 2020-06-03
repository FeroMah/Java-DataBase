package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter

public class ProductSeedDTO {
    @Expose
    @Length(min = 3, message = "Product name is too short!")
    private String name;
    @Expose
    private BigDecimal price;
}
