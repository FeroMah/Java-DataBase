package softuni.homework.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.homework.dtos.exports.suppliers.SupplierExportDTO;
import softuni.homework.dtos.exports.suppliers.SupplierExportRootDTO;
import softuni.homework.dtos.imports.suppliers.SupplierImportDTO;
import softuni.homework.dtos.imports.suppliers.SupplierRootImportDTO;
import softuni.homework.entities.Supplier;
import softuni.homework.repositories.SupplierRepository;
import softuni.homework.services.SupplierService;
import softuni.homework.utils.ValidationUtil;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final ValidationUtil validationUtil;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Random random;

    @Autowired
    public SupplierServiceImpl(ValidationUtil validationUtil, SupplierRepository supplierRepository, ModelMapper modelMapper, Random random) {
        this.validationUtil = validationUtil;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.random = random;
    }

    @Override
    public void seedSuppliers(SupplierRootImportDTO supplierRootImportDTO) {

        supplierRootImportDTO.getSuppliers().forEach(supplierImportDTO -> {

            if (this.validationUtil.isValid(supplierImportDTO)) {
                addSupplierToDB(supplierImportDTO);
            } else {
                this.validationUtil.violations(supplierImportDTO)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });
    }

    @Override
    public Supplier getRandomSupplier() {
        long randomId = this.random.nextInt((int) this.supplierRepository.count()) + 1;
        return this.supplierRepository.getOne(randomId);
    }

    @Override
    public SupplierExportRootDTO getAllLocalSuppliers() {

        List<Supplier> localSuppliers = this.supplierRepository.findAllByIsImporterFalse();

        List<SupplierExportDTO> supplierExportDTOS = localSuppliers
                .stream()
                .map(s -> {
                    SupplierExportDTO supplierDTO = this.modelMapper.map(s, SupplierExportDTO.class);
                    supplierDTO.setPartsCount(s.getPartList().size());
                    return supplierDTO;
                })
                .collect(Collectors.toList());

        return new SupplierExportRootDTO(supplierExportDTOS);
    }

    private void addSupplierToDB(SupplierImportDTO supplierImportDTO) {

        if (isUnique(supplierImportDTO)) {
            Supplier supplier = this.modelMapper.map(supplierImportDTO, Supplier.class);
            this.supplierRepository.saveAndFlush(supplier);
            System.out.println(String.format("SEED Supplier: %s", supplier.getName()));
        } else {
            System.out.println(String.format("ALREADY EXIST Supplier : %s ", supplierImportDTO.getName()));
        }
    }

    private boolean isUnique(SupplierImportDTO supplierImportDTO) {
        return this.supplierRepository.findAllByName(supplierImportDTO.getName()).isEmpty();
    }


}
