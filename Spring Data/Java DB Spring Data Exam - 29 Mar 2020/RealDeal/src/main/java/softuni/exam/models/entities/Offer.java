package softuni.exam.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import softuni.exam.models.entities.baseId.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter

@Entity(name = "offers")
public class Offer extends BaseEntity {

    @Column
    @DecimalMin(value = "0.00")
    private BigDecimal price;

//    @Column(columnDefinition = "text") don't if it works must be tested
    @Column
    @Lob
    @Length(min = 5)
    private String description;

    @Column(name = "has_gold_status")
    @NotNull
    private Boolean hasGoldStatus;


    @Column(name = "added_on")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime addedOn;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private Seller seller;

    @ManyToMany(targetEntity = Picture.class, mappedBy = "offers", cascade = CascadeType.ALL)
    private List<Picture> pictures;


}
