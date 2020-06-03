package softuni.exam.models.dtos.offer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import softuni.exam.adaptors.LocalDateTimeAdapter;

import javax.validation.constraints.DecimalMin;
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

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDTO {

    @XmlElement
    @Length(min = 5)
    private String description;

    @XmlElement
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @XmlElement(name = "added-on")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @NotNull
    private LocalDateTime addedOn;

    @XmlElement(name = "has-gold-status")
    @NotNull
    private Boolean hasGoldStatus;

    @XmlElement
    private OfferCarSeedDTO car;

    @XmlElement
    private OfferSellerSeedDTO seller;
}
