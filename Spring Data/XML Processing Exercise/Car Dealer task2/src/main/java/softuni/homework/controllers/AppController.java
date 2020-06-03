package softuni.homework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.homework.dtos.exports.cars.CarExportRootDTO;
import softuni.homework.dtos.exports.customers.CustomerExportRootDTO;
import softuni.homework.dtos.exports.suppliers.SupplierExportRootDTO;
import softuni.homework.dtos.imports.cars.CarImportRootDTO;
import softuni.homework.dtos.imports.customers.CustomerImportRootDTO;
import softuni.homework.dtos.imports.parts.PartImportRootDTO;
import softuni.homework.dtos.imports.suppliers.SupplierRootImportDTO;
import softuni.homework.services.*;
import softuni.homework.utils.FileIOUtil;
import softuni.homework.utils.ValidationUtil;
import softuni.homework.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

import static softuni.homework.constants.GlobalFileLocation.*;

@Controller
public class AppController implements CommandLineRunner {
    private final XmlParser xmlParser;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;


    @Autowired
    public AppController(XmlParser xmlParser, FileIOUtil fileIOUtil, ValidationUtil validationUtil, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.xmlParser = xmlParser;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.seedSupplier();
        this.seedPart();
        this.seedCars();
        this.seedCustomers();
        this.seedSales();

//        Ex1
//        this.getOrderedCustomers();

//        Ex2
//        this.getCarsFromMakeToyota();

//        Ex3
        this.getLocalSuppliers();


    }

    private void getLocalSuppliers() throws JAXBException {
        SupplierExportRootDTO allLocalSuppliers = this.supplierService.getAllLocalSuppliers();
        this.xmlParser.marshalToFile(Q3_LOCAL_SUPPLIERS_FILE_PATH, allLocalSuppliers);
        System.out.println("Check solution in q3_local_suppliers.xml");
    }

    private void getCarsFromMakeToyota() throws JAXBException {
        CarExportRootDTO allByMakerToyota = this.carService.getAllByMakerToyota();
        this.xmlParser.marshalToFile(Q2_CARS_FROM_MAKER_TOYOTA_FILE_PATH, allByMakerToyota);
        System.out.println("Check solution in q2_cars_from_maker_toyota.xml");
    }

    private void getOrderedCustomers() throws JAXBException {
        CustomerExportRootDTO customerExportRootDTO =
                this.customerService.getAllCustomersSortByDateOfBirthAscAndSortByIsYoungDriverDesc();
        this.xmlParser.marshalToFile(Q1_ORDERED_CUSTOMERS_FILE_PATH, customerExportRootDTO);

        System.out.println("Check solution in q1_ordered_customers.xml");
    }

    private void seedSales() {
        this.saleService.seedSales();
    }

    private void seedCustomers() throws JAXBException, FileNotFoundException {
//        TODO
        CustomerImportRootDTO customerImportRootDTO =
                this.xmlParser.unmarshalFromFile(CUSTOMERS_FILE_PATH, CustomerImportRootDTO.class);
        this.customerService.seedCustomers(customerImportRootDTO);
    }

    private void seedCars() throws JAXBException, FileNotFoundException {
        CarImportRootDTO carImportRootDTO =
                this.xmlParser.unmarshalFromFile(CARS_FILE_PATH, CarImportRootDTO.class);
        this.carService.seedCars(carImportRootDTO);
    }

    private void seedPart() throws JAXBException, FileNotFoundException {
        PartImportRootDTO partImportRootDTO =
                this.xmlParser.unmarshalFromFile(PARTS_FILE_PATH, PartImportRootDTO.class);
        this.partService.seedParts(partImportRootDTO);

    }

    private void seedSupplier() throws JAXBException, FileNotFoundException {
        SupplierRootImportDTO supplierRootImportDTO =
                this.xmlParser.unmarshalFromFile(SUPPLIERS_FILE_PATH, SupplierRootImportDTO.class);
        this.supplierService.seedSuppliers(supplierRootImportDTO);
    }
}
