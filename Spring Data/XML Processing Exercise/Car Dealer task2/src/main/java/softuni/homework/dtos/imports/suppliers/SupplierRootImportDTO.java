package softuni.homework.dtos.imports.suppliers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "suppliers")
@NoArgsConstructor
@Getter
@Setter
public class SupplierRootImportDTO {

    @XmlElement(name = "supplier")
    List<SupplierImportDTO> suppliers;
}
