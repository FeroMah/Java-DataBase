package softuni.homework.services;

import softuni.homework.dtos.exports.customers.CustomerExportRootDTO;
import softuni.homework.dtos.imports.customers.CustomerImportRootDTO;
import softuni.homework.entities.Customer;

public interface CustomerService {

    void seedCustomers(CustomerImportRootDTO customerImportRootDTO);

    Customer getRandomCustomer ();

    CustomerExportRootDTO getAllCustomersSortByDateOfBirthAscAndSortByIsYoungDriverDesc ();
}
