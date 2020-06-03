package softuni.homework.dtos.exports.suppliers;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.homework.entities.Part;
import softuni.homework.entities.baseId.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter

@XmlRootElement(name = "supplier")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierExportDTO {

    @XmlAttribute
    private Long id;

    @XmlAttribute
    private String name;

    @XmlAttribute(name = "parts-count")
    private Integer partsCount;


}
