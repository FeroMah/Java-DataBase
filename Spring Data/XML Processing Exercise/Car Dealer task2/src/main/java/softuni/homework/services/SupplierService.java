package softuni.homework.services;

import softuni.homework.dtos.exports.suppliers.SupplierExportRootDTO;
import softuni.homework.dtos.imports.suppliers.SupplierRootImportDTO;
import softuni.homework.entities.Supplier;

public interface SupplierService {

    void seedSuppliers(SupplierRootImportDTO supplierRootImportDTO);

    Supplier getRandomSupplier ();


    SupplierExportRootDTO getAllLocalSuppliers();


}
