package softuni.homework.dtos.imports.cars;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cars")
public class CarImportRootDTO {
    @XmlElement(name = "car")
    List<CarImportDTO> cars;
}
