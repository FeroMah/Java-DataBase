package softuni.homework.dtos.imports.parts;


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
@XmlRootElement(name = "parts")
public class PartImportRootDTO {
    @XmlElement(name = "part")
    private List<PartImportDTO> parts;
}
