package softuni.json_processing_exercise.services;

import softuni.json_processing_exercise.dtos.ProductExportDTO;
import softuni.json_processing_exercise.dtos.ProductSeedDTO;

import java.util.List;


public interface ProductService {

    void seedProducts(ProductSeedDTO[] productSeedDTOs);

    List<ProductExportDTO> getBetweenPrice500And100WithoutBuyer();

}
