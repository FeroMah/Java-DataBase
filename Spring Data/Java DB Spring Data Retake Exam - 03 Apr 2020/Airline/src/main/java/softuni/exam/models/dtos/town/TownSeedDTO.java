package softuni.exam.models.dtos.town;



import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter

public class TownSeedDTO {

    @Expose
    @Size(min = 2)
    @NotNull
    private String name ;

    @Expose
    @Min(value = 0)
    private Long population;

    @Expose
    @Lob
    private String guide;
}
