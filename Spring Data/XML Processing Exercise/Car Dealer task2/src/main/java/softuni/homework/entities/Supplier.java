package softuni.homework.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.homework.entities.baseId.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class Supplier extends BaseEntity {

    @Column
    @NotNull(message = "Name can't be null!")
    private String name;

    @Column(name = "is_importer")
    private boolean isImporter;

    @OneToMany(mappedBy = "supplier",fetch = FetchType.EAGER)
    // use 'mappedBy' for bidirectional relation example: mappedBy = "{name of the field from other side}"
    private List<Part> partList;


}
