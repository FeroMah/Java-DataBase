package softuni.homework.dtos.exports.cars;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import softuni.homework.entities.baseId.BaseEntity;

import javax.xml.bind.annotation.*;


@NoArgsConstructor
@Getter
@Setter

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportDTO {

    @XmlAttribute
    private Long id;

    @XmlAttribute
    private String make;

    @XmlAttribute
    private String model;

    @XmlAttribute(name = "travelled-distance")
    private Long travelledDistance;

}
