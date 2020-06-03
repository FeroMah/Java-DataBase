package softuni.homework.dtos.exports.customers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.homework.adaptors.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerExportDTO {
    @XmlElement
    private Long id;

    @XmlElement
    private String name;

    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateOfBirth;

    @XmlElement(name = "is-young-driver")
    private Boolean isYoungDriver;
}
