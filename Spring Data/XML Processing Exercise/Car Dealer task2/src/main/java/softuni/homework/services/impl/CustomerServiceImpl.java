package softuni.homework.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.homework.dtos.exports.customers.CustomerExportDTO;
import softuni.homework.dtos.exports.customers.CustomerExportRootDTO;
import softuni.homework.dtos.imports.customers.CustomerImportDTO;
import softuni.homework.dtos.imports.customers.CustomerImportRootDTO;
import softuni.homework.entities.Customer;
import softuni.homework.repositories.CustomerRepository;
import softuni.homework.services.CustomerService;
import softuni.homework.utils.ValidationUtil;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Random random;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Random random) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.random = random;
    }

    @Override
    public void seedCustomers(CustomerImportRootDTO customerImportRootDTO) {
        customerImportRootDTO.getCustomers().forEach(customerImportDTO -> {
                    if (this.validationUtil.isValid(customerImportDTO)) {
                        addCustomerToDB(customerImportDTO);
                    } else {
                        this.validationUtil.violations(customerImportDTO)
                                .stream()
                                .map(ConstraintViolation::getMessage)
                                .forEach(System.out::println);
                    }
                }
        );
    }

    @Override
    public Customer getRandomCustomer() {
        long randomIndex = this.random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.findById(randomIndex);
    }

    @Override
    public CustomerExportRootDTO getAllCustomersSortByDateOfBirthAscAndSortByIsYoungDriverDesc() {

        List<Customer> customers = this.customerRepository.
                findAllDriverOrderByDateOfBirthAndIsYoungDriverDesc();

        List<CustomerExportDTO> customerExportDTOS = customers.stream()
                .map(c -> this.modelMapper.map(c, CustomerExportDTO.class))
                .collect(Collectors.toList());

        return new CustomerExportRootDTO(customerExportDTOS);
    }

    private void addCustomerToDB(CustomerImportDTO customerImportDTO) {
        if (isUnique(customerImportDTO)) {
            Customer customer = this.modelMapper.map(customerImportDTO, Customer.class);
            this.customerRepository.saveAndFlush(customer);
            System.out.println(String.format("SEED Customer: %s", customer.getName()));
        } else {
            System.out.println(String.format("ALREADY EXIST Customer : %s ", customerImportDTO.getName()));
        }
    }

    private boolean isUnique(CustomerImportDTO customerImportDTO) {
        return this.customerRepository.findAllByNameAndDateOfBirth
                (customerImportDTO.getName(), customerImportDTO.getDateOfBirth())
                .isEmpty();
    }
}
