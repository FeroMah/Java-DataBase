package softuni.exam.models.dtos.ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.adaptors.LocalDateTimeAdapter;


import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ticket")
public class TicketSeedDTO {

    @XmlElement(name = "serial-number")
    @NotNull
    private String serialNumber;

    @XmlElement
    @NotNull
    private BigDecimal price;

    @XmlElement(name = "take-off")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @NotNull
    private LocalDateTime takeOff;

    @XmlElement(name = "from-town")
    @NotNull
    private FromTownXmlDTO fromTown;

    @XmlElement(name = "to-town")
    @NotNull
    private FromTownXmlDTO toTown;

    @XmlElement
    @NotNull
    private PlaneXmlDTO plane;

    @XmlElement
    @NotNull
    private PassengerXmlDTO passenger;

}
