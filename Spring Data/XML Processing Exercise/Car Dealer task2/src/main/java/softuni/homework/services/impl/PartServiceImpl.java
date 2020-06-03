package softuni.homework.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.homework.dtos.imports.parts.PartImportDTO;
import softuni.homework.dtos.imports.parts.PartImportRootDTO;
import softuni.homework.entities.Part;
import softuni.homework.entities.Supplier;
import softuni.homework.repositories.PartRepository;
import softuni.homework.services.PartService;
import softuni.homework.services.SupplierService;
import softuni.homework.utils.ValidationUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.*;
@Service
@Transactional
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final SupplierService supplierService;
    private final Random random;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, ValidationUtil validationUtil, SupplierService supplierService, Random random) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.supplierService = supplierService;
        this.random = random;
    }

    @Override
    public void seedParts(PartImportRootDTO partImportRootDTO) {

        partImportRootDTO.getParts().forEach(partImportDTO -> {

            if (this.validationUtil.isValid(partImportDTO)) {

                addPartToDB(partImportDTO);
            } else {
                this.validationUtil.violations(partImportDTO)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }

        });
    }

    @Override
    @Transactional
    public List<Part> getRandomParts() {
        List<Part> resultList = new ArrayList<>();
        long randomCounter = this.random.nextInt(11) + 10;

        for (int i = 0; i < randomCounter; i++) {
            Long randomId = (long) (this.random
                    .nextInt((int) this.partRepository.count()) + 1);
            Part one = this.partRepository.getById(randomId);
            resultList.add(this.partRepository.getById(randomId));

        }

        return resultList;
    }

    private void addPartToDB(PartImportDTO partImportDTO) {
        if (isUnique(partImportDTO)) {
            Part part = this.modelMapper.map(partImportDTO, Part.class);
            this.setRandomSupplier(part);
            this.partRepository.saveAndFlush(part);
            System.out.println(String.format("SEED Part: %s", part.getName()));
        } else {
            System.out.println(String.format("ALREADY EXIST Part : %s ", partImportDTO.getName()));
        }
    }

    private void setRandomSupplier(Part part) {
        Supplier randomSupplier = this.supplierService.getRandomSupplier();
        part.setSupplier(randomSupplier);
    }

    private boolean isUnique(PartImportDTO partImportDTO) {
        return this.partRepository.findAllByNameAndPrice(partImportDTO.getName(), partImportDTO.getPrice()).isEmpty();
    }
}
