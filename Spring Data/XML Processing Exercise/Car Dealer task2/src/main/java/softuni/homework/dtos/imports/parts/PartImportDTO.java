package softuni.homework.dtos.imports.parts;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "part")
public class PartImportDTO {

    @XmlAttribute
    @NotNull(message = "Name can't be null!")
    private String name;

    @XmlAttribute
    @DecimalMin(value = "0.0", message = "Price can't be negative number!")
    private BigDecimal price;

    @XmlAttribute
    @Min(value = 0, message = "Quantity can't be negative number!")
    private Integer quantity;
}
