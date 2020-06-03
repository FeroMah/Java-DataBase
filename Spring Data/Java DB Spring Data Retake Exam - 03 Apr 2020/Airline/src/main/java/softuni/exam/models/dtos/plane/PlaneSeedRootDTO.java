package softuni.exam.models.dtos.plane;

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
@XmlRootElement (name = "planes")
public class PlaneSeedRootDTO {
    @XmlElement(name = "plane")
    private List<PlaneSeedDTO> planes;
}
