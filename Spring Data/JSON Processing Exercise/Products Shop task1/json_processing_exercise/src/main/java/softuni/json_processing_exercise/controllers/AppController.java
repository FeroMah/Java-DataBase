package softuni.json_processing_exercise.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.json_processing_exercise.dtos.*;
import softuni.json_processing_exercise.services.CategoryService;
import softuni.json_processing_exercise.services.ProductService;
import softuni.json_processing_exercise.services.UserService;
import softuni.json_processing_exercise.utils.FileIOUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static softuni.json_processing_exercise.GlobalConstants.*;

@Controller
public class AppController implements CommandLineRunner {
    private final Gson gson;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductService productService;
    private final FileIOUtil fileIOUtil;

    @Autowired
    public AppController(Gson gson, CategoryService categoryService, UserService userService, ProductService productService, FileIOUtil fileIOUtil) {
        this.gson = gson;
        this.categoryService = categoryService;
        this.userService = userService;
        this.productService = productService;
        this.fileIOUtil = fileIOUtil;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedCategories();
        this.seedUsers();
        this.seedProducts();

//        Ex1
//        this.writeProductsInRange ();

//        Ex2
//        this.getSuccessfullySoldProducts();

//        Ex3
//        this.getCategoriesByProductsCountTask3();

//        Ex4
//        this.getUsersAndProducts();


    }

    private void getUsersAndProducts() throws IOException {
//        TODO
        UsersAndProductsDTO1 usersWithSuccessfulSellAndTheirProducts = this.userService.getUsersWithSuccessfulSellAndTheirProducts();
        String json = this.gson.toJson(usersWithSuccessfulSellAndTheirProducts);
        this.fileIOUtil.write(json, Q4_USERS_AND_PRODUCTS_FILE_PATH);
    }

    private void getCategoriesByProductsCountTask3() throws IOException {
        List<CategoryExportDTO> categoriesByProductsCount = this.categoryService.getCategoriesByProductsCount();
        String json = this.gson.toJson(categoriesByProductsCount);
        System.out.println(json);
        this.fileIOUtil.write(json, Q3_CATEGORIES_BY_PRODUCTS_COUNT_FILE_PATH);
    }

    private void getSuccessfullySoldProducts() throws IOException {
        List<SellerExportDTO> sellers = this.userService.getSuccessfullSellers();
        System.out.println(this.gson.toJson(sellers));
        this.fileIOUtil.write(this.gson.toJson(sellers), Q2_SUCCESSFULLY_SOLD_PRODUCTS_FILE_PATH);
    }

    private void writeProductsInRange() throws IOException {
        List<ProductExportDTO> dtos = this.productService.getBetweenPrice500And100WithoutBuyer();

//        ToDo remember:  gson.toJson work with object a.k.a. arrays/collections
//        StringBuilder sb = new StringBuilder();
//        dtos.forEach(dto->{
//            String json = this.gson.toJson(dto);
//
//        } );

        this.fileIOUtil.write(this.gson.toJson(dtos), Q1_PRODUCTS_IN_RANGE_FILE_PATH);
    }

    private void seedUsers() throws FileNotFoundException {
        UserSeedDTO[] dtos = this.gson.fromJson(new FileReader(USERS_FILE_PATH), UserSeedDTO[].class);
        System.out.println();
        this.userService.seedUsers(dtos);

    }

    private void seedCategories() throws FileNotFoundException {
        CategorySeedDTO[] dtos = this.gson.fromJson(new FileReader(CATEGORIES_FILE_PATH), CategorySeedDTO[].class);
        this.categoryService.seedCategories(dtos);
    }

    private void seedProducts() throws FileNotFoundException {
        ProductSeedDTO[] dtos = this.gson.fromJson(new FileReader(PRODUCTS_FILE_PATH), ProductSeedDTO[].class);
        this.productService.seedProducts(dtos);
    }

}
