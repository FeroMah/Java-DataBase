package softuni.json_processing_exercise.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(nullable = false)
    @Length(min = 3, max = 15, message = "Category's name must be between 3 and 15 symbols max!")
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<Product>();
}
