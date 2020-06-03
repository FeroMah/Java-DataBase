package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class SellerExportDTO {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private List< SoldProductDTO> soldProducts;
}
