package softuni.json_processing_exercise.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.json_processing_exercise.dtos.*;
import softuni.json_processing_exercise.entities.Product;
import softuni.json_processing_exercise.entities.User;
import softuni.json_processing_exercise.repositories.UserRepository;
import softuni.json_processing_exercise.services.UserService;
import softuni.json_processing_exercise.utils.ValidationUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedUsers(UserSeedDTO[] userSeedDTOs) {
//        if (this.userRepository.count()!=0){
//            return;
//        }

        Arrays.stream(userSeedDTOs).forEach(userSeedDTO -> {
            if (!isUserUnique(userSeedDTO)) {
                System.out.println(String.format("USER: %s %s  already exist!", userSeedDTO.getFirstName(), userSeedDTO.getLastName()));
                return;
            }
            if (this.validationUtil.isValid(userSeedDTO)) {
                User user = this.modelMapper.map(userSeedDTO, User.class);
                this.userRepository.saveAndFlush(user);
            } else {
                this.validationUtil.violations(userSeedDTO).stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            }
        });
    }

    private boolean isUserUnique(UserSeedDTO userSeedDTO) {
        return this.userRepository.findAllByFirstNameAndLastNameAndAge(userSeedDTO.getFirstName(), userSeedDTO.getLastName(), userSeedDTO.getAge()).isEmpty();
    }

    @Override
    public User getRandomUser() {
        Random random = new Random();
        long randomId = random.nextInt((int) this.userRepository.count()) + 1;
        return this.userRepository.getOne(randomId);
    }

    public List<SellerExportDTO> getSuccessfullSellers() {

//        test only!
//        List<User> distinctBySold_buyerIsNotNullOrderByLastNameAscFirstNameAsc = this.userRepository.findDistinctBySold_BuyerIsNotNullOrderByLastNameAscFirstNameAsc();
//        System.out.println();
//        test only!

        List<User> users = this.userRepository.getUsersWithAtLeastOneProductSold();
        List<SellerExportDTO> collect = users.stream().map(x -> {
            SellerExportDTO seller = this.modelMapper.map(x, SellerExportDTO.class);
            seller.setSoldProducts(
                    x.getSold().stream()
                            .filter(b -> b.getBuyer() != null)
                            .map(product -> this.modelMapper.map(product, SoldProductDTO.class))
                            .collect(Collectors.toList())
            );

            return seller;
        }).collect(Collectors.toList());
        return collect;


    }

    @Override
    public UsersAndProductsDTO1 getUsersWithSuccessfulSellAndTheirProducts() {
        List<User> usersWithAtLeastOneProductSold = this.userRepository.getUsersWithAtLeastOneProductSold();
        long size = usersWithAtLeastOneProductSold.size();


        List<UserAndSoldProductsDTO2> collectionOfDTO2 = usersWithAtLeastOneProductSold
                .stream()
                .map(user -> {
            UserAndSoldProductsDTO2 mappedDTO2 = this.modelMapper.map(user, UserAndSoldProductsDTO2.class);

            List<Product> products = new ArrayList<>(user.getSold());
                    System.out.println();
            List<ProductDTO4> productDTO4s = products.stream()
                    .map(p -> this.modelMapper.map(p, ProductDTO4.class))
                    .collect(Collectors.toList());

            long productsCount = productDTO4s.size();
            ProductsCountDTO3 productsCountDTO3 = new ProductsCountDTO3(productsCount, productDTO4s);

            mappedDTO2.setSoldProducts(productsCountDTO3);

            return mappedDTO2;
        }).collect(Collectors.toList());

        UsersAndProductsDTO1 usersAndProductsDTO1 = new UsersAndProductsDTO1(size, collectionOfDTO2);

        return usersAndProductsDTO1;
    }

}