package softuni.homework.dtos.imports.customers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.homework.adaptors.LocalDateTimeAdapter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "customer")
public class CustomerImportDTO {
    @XmlAttribute
    @Length(min = 3, message = "Name is too short {min=3}!")
    private String name;

    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @NotNull(message = "Birth Date can't be null!")
    private LocalDateTime dateOfBirth;

    @XmlElement(name = "is-young-driver")
    private Boolean isYoungDriver;
}
