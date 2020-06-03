package softuni.exam.models.dtos.offer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@Getter
@Setter

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferCarSeedDTO {
    @XmlElement
    private Long id;

}
