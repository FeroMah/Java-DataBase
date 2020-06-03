package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductsCountDTO3 {
    @Expose
    private Long count;
    @Expose
    private List<ProductDTO4> products;

}
