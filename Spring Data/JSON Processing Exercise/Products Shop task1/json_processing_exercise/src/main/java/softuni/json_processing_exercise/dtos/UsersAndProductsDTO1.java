package softuni.json_processing_exercise.dtos;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter

public class UsersAndProductsDTO1 {
    @Expose
    private Long usersCount;
    @Expose
    private List<UserAndSoldProductsDTO2> users;
}
