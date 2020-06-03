package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CategoryExportDTO  {

    @Expose
    private String category;

    @Expose
    private long productCount;

    @Expose
    private double averagePrice;

    @Expose
    private double totalRevenue;
}
