package softuni.json_processing_exercise.services.impls;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.json_processing_exercise.dtos.CategoryExportDTO;
import softuni.json_processing_exercise.dtos.CategorySeedDTO;
import softuni.json_processing_exercise.entities.Category;
import softuni.json_processing_exercise.repositories.CategoryRepository;
import softuni.json_processing_exercise.services.CategoryService;
import softuni.json_processing_exercise.utils.ValidationUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(CategorySeedDTO[] categorySeedDTOs) {


//        if (this.categoryRepository.count() != 0) {
//            return;
//        }

        Arrays.stream(categorySeedDTOs).forEach(categorySeedDTO -> {

            if (!isCategoryUnique(categorySeedDTO)) {
                System.out.println(String.format("CATEGORY: %s  already exist!", categorySeedDTO.getName()));
                return;
            }

            if (this.validationUtil.isValid(categorySeedDTO)) {
                Category category = this.modelMapper.map(categorySeedDTO, Category.class);
                this.categoryRepository.saveAndFlush(category);
            } else {
                this.validationUtil.violations(categorySeedDTO).stream().map(ConstraintViolation::getMessage).forEach(System.out::println);
            }

        });
    }

    @Override
    public Set<Category> getRandomCategory() {
        Random random = new Random();
        Set<Category> randomCategories = new HashSet<>();
        int randomCount = random.nextInt(3) + 1;
        while (randomCategories.size() < randomCount) {
            long randomId = random.nextInt((int) this.categoryRepository.count()) + 1;
            randomCategories.add(this.categoryRepository.getOne(randomId));
        }
        return randomCategories;
    }

    @Override
    public List<CategoryExportDTO> getCategoriesByProductsCount() {
        List<Category> all = this.categoryRepository.findAll();

        List<CategoryExportDTO> collect = all.stream().map(category -> {
            CategoryExportDTO categoryExportDTO = new CategoryExportDTO();
            long size = category.getProducts().size();
            double revenue = category.getProducts().stream().mapToDouble(x -> x.getPrice().doubleValue()).sum();
            double aver = revenue / size;

            categoryExportDTO.setCategory(category.getName());
            categoryExportDTO.setProductCount(size);
            categoryExportDTO.setAveragePrice(aver);
            categoryExportDTO.setTotalRevenue(revenue);
            return categoryExportDTO;
        }).sorted(Comparator.comparing(CategoryExportDTO::getProductCount, Comparator.reverseOrder())).collect(Collectors.toList());
        return collect;

    }

    private boolean isCategoryUnique(CategorySeedDTO categorySeedDTO) {
        return this.categoryRepository.findCategoryByName(categorySeedDTO.getName()).isEmpty();
    }
}
