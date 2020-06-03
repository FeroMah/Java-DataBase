package softuni.exam.models.dtos.ticket;

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


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "plane")
public class PlaneXmlDTO {

    @XmlElement(name = "register-number")
    private String registerNumber;
}
