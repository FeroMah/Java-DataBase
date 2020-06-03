package softuni.exam.models.dtos.saller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.constats.Rating;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.*;

@NoArgsConstructor
@Getter
@Setter


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "seller")
public class SellerSeedDTO {

    @XmlElement
    @Pattern(regexp = ".+@.+\\..+")
    private String email;


    @XmlElement(name="first-name")
    @Length(min = 2, max = 20)
    private String firstName;

    @XmlElement(name="last-name")
    @Length(min = 2, max = 20)
    private String lastName;

    @XmlElement(required = true )
    @NotNull
    private Rating rating;

    @XmlElement(required = true)
    private String town;


}
