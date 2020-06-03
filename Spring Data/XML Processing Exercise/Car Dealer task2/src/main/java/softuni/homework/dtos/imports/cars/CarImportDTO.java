package softuni.homework.dtos.imports.cars;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@NoArgsConstructor
@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "car")
public class CarImportDTO {

    @XmlElement
    @NotNull(message = "Make can't be null")
    private String make;

    @XmlElement
    @NotNull(message = "Model can't be null")
    private String model;

    @XmlElement(name = "travelled-distance")
    @Min(value = 0, message = "Travelled distance can't be negative number!")
    private Long travelledDistance;
}
