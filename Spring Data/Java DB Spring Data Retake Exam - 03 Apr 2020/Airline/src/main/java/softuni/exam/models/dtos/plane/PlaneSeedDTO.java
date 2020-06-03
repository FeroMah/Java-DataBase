package softuni.exam.models.dtos.plane;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@Getter
@Setter

@XmlRootElement(name = "plane")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedDTO {

    @XmlElement(name = "register-number")
    @Size(min = 5)
    @NotNull
    private String registerNumber;

    @XmlElement
    @Min(value = 0)
    @NotNull
    private Integer capacity;

    @XmlElement
    @Size(min = 2)
    @NotNull
    private String airline;
}
