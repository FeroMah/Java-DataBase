package softuni.homework.dtos.imports.suppliers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@NoArgsConstructor
@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)

public class SupplierImportDTO {

    @XmlAttribute
    @NotNull
    private String name;

    @XmlAttribute(name = "is-importer")
    private boolean isImporter;
}
