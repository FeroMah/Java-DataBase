package softuni.homework.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.homework.dtos.exports.cars.CarExportDTO;
import softuni.homework.dtos.exports.cars.CarExportRootDTO;
import softuni.homework.dtos.imports.cars.CarImportDTO;
import softuni.homework.dtos.imports.cars.CarImportRootDTO;
import softuni.homework.entities.Car;
import softuni.homework.entities.Part;
import softuni.homework.repositories.CarRepository;
import softuni.homework.services.CarService;
import softuni.homework.services.PartService;
import softuni.homework.utils.ValidationUtil;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final PartService partService;
    private final Random random;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, ModelMapper modelMapper, PartService partService, Random random) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.partService = partService;
        this.random = random;
    }

    @Override
    public void seedCars(CarImportRootDTO carImportRootDTO) {

        carImportRootDTO.getCars().forEach(carImportDTO -> {

            if (this.validationUtil.isValid(carImportDTO)) {
                this.addCarToDB(carImportDTO);
            } else {
                this.validationUtil.violations(carImportDTO)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }
        });
    }

    @Override
    public Car getRandomCar() {
        long randomIndex = this.random.nextInt((int) this.carRepository.count()) + 1;
        return this.carRepository.findById(randomIndex);
    }

    @Override
    public CarExportRootDTO getAllByMakerToyota() {
        List<Car> toyotas = this.carRepository.
                findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");

        List<CarExportDTO> carExportDTOS = toyotas.stream()
                .map(t -> this.modelMapper.map(t, CarExportDTO.class))
                .collect(Collectors.toList());

      return   new CarExportRootDTO(carExportDTOS);


    }

    private void addCarToDB(CarImportDTO carImportDTO) {
        if (isUnique(carImportDTO)) {
            Car car = this.modelMapper.map(carImportDTO, Car.class);
            List<Part> randomParts = this.partService.getRandomParts();
            car.setPartsList(randomParts);

            randomParts.forEach(part -> {
                part.getCarsList().add(car);
            });

            this.carRepository.saveAndFlush(car);
            System.out.println(String.format("SEED Car: %s %s", car.getMake(), car.getModel()));
        } else {
            System.out.println(String.format("ALREADY EXIST Car: %s %s", carImportDTO.getMake(), carImportDTO.getModel()));
        }
    }


    private boolean isUnique(CarImportDTO carImportDTO) {
        return this.carRepository.findAllByMakeAndModelAndTravelledDistance(
                carImportDTO.getMake(),
                carImportDTO.getModel(),
                carImportDTO.getTravelledDistance())
                .isEmpty();
    }
}
