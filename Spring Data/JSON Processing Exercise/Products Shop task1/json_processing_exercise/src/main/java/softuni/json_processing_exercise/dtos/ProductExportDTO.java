package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProductExportDTO {

    @Expose
    private String name;

    @Expose
    private BigDecimal price;

    @Expose
    private String sellerFullName;
}
