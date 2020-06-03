package softuni.exam.models.dtos.picture;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entities.Car;

@NoArgsConstructor
@Getter
@Setter

public class PictureStringNODATEdto {
//      "name": "mZ_zT_oH",
//              "dateAndTime": "2014-06-10 03:31:39",
//              "car": 62
    @Expose
    private String name;

    @Expose
    private String dateAndTime;

    @Expose
    private Long car;
}
