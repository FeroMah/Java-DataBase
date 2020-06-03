package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter

public class ProductDTO4 {

    @Expose
    private String  name;
    @Expose
    private double price;

}
