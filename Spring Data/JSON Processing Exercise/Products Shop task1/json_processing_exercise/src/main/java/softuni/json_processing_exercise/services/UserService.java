package softuni.json_processing_exercise.services;


import softuni.json_processing_exercise.dtos.SellerExportDTO;
import softuni.json_processing_exercise.dtos.UserSeedDTO;
import softuni.json_processing_exercise.dtos.UsersAndProductsDTO1;
import softuni.json_processing_exercise.entities.User;

import java.util.List;
import java.util.Set;


public interface UserService {

    void seedUsers(UserSeedDTO[] userSeedDTOs);

    User getRandomUser();

    List<SellerExportDTO> getSuccessfullSellers();

    UsersAndProductsDTO1 getUsersWithSuccessfulSellAndTheirProducts();

}
