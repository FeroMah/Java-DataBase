package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter

public class UserAndSoldProductsDTO2 {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private ProductsCountDTO3 soldProducts;


    public UserAndSoldProductsDTO2(String firstName, String lastName, int age, ProductsCountDTO3 soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
    }

}
