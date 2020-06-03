package softuni.json_processing_exercise.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.json_processing_exercise.dtos.ProductExportDTO;
import softuni.json_processing_exercise.dtos.ProductSeedDTO;

import softuni.json_processing_exercise.entities.Product;
import softuni.json_processing_exercise.entities.User;
import softuni.json_processing_exercise.repositories.ProductRepository;
import softuni.json_processing_exercise.services.CategoryService;
import softuni.json_processing_exercise.services.ProductService;
import softuni.json_processing_exercise.services.UserService;
import softuni.json_processing_exercise.utils.ValidationUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Transactional  // must be added when entity has relations to other entity
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ValidationUtil validationUtil, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public void seedProducts(ProductSeedDTO[] productSeedDTOs) {
//        Product is not necessary to be unique
        if (this.productRepository.count() != 0) {
            return;
        }


        Arrays.stream(productSeedDTOs).forEach(productSeedDTO -> {

            if (this.validationUtil.isValid(productSeedDTO)) {
                Product product = this.modelMapper.map(productSeedDTO, Product.class);

                product.setCategories(this.categoryService.getRandomCategory());

//                TODO random bayer(must have null) and seller
                User randomSeller = this.userService.getRandomUser();
                product.setSeller(randomSeller);
                User randomBuyer = this.getRandomBuyer();
                product.setBuyer(randomBuyer);


                this.productRepository.saveAndFlush(product);
            } else {
                this.validationUtil.violations(productSeedDTO).stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            }
        });


    }

    @Override
    public List<ProductExportDTO> getBetweenPrice500And100WithoutBuyer() {
        Set<Product> allByPriceBetweenAndBuyerIsNull = this.productRepository.
                getAllByPriceBetweenAndBuyerIsNull(new BigDecimal(500), new BigDecimal(1000));
        return allByPriceBetweenAndBuyerIsNull.stream().map(product -> {
            String fullName = String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName());
            ProductExportDTO productExportDTO = this.modelMapper.map(product, ProductExportDTO.class);
            productExportDTO.setSellerFullName(fullName);
            return productExportDTO;
        }).collect(Collectors.toList());
    }

    public User getRandomBuyer() {
        Random random = new Random();
        int randomNum = random.nextInt(2);
        if (randomNum == 1) {
            return null;
        }
        return this.userService.getRandomUser();
    }


}
