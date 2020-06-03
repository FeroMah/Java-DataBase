package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.car.CarSeedDTO;
import softuni.exam.models.dtos.car.CasImportStringDATEdto;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.FileIOUtil;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constats.GlobalFilePaths.CARS_FILE_PATH;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final FileIOUtil fileIOUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, FileIOUtil fileIOUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return this.fileIOUtil.readFileContent(CARS_FILE_PATH);
    }

    @Override
    public String importCars() throws IOException {

        StringBuilder info = new StringBuilder();

        CasImportStringDATEdto[] casImportStringDATEdtos = this.gson.fromJson(new FileReader(CARS_FILE_PATH), CasImportStringDATEdto[].class);

        Arrays.stream(casImportStringDATEdtos).forEach(casImportStringDATEdto -> {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(casImportStringDATEdto.getRegisteredOn(), formatter);

            CarSeedDTO carSeedDTO = new CarSeedDTO();
            carSeedDTO.setKilometers(casImportStringDATEdto.getKilometers());
            carSeedDTO.setMake(casImportStringDATEdto.getMake());
            carSeedDTO.setModel(casImportStringDATEdto.getModel());
            carSeedDTO.setRegisteredOn(date);

            if (this.validationUtil.isValid(carSeedDTO)) {
                Car car = this.modelMapper.map(carSeedDTO, Car.class);
                this.carRepository.saveAndFlush(car);
                info.append(String.format("Successfully imported car - %s - %s", car.getMake(), car.getModel()));
            } else {
                info.append("Invalid car");
            }
            info.append(System.lineSeparator());

        });

        return info.toString();
    }

    public Car getCarById(Long id) {
        return this.carRepository.getById(id);
    }


    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder result = new StringBuilder();
        List<Car> cars = this.carRepository.findAllCarsOrderByPicturesCountDecsAndMakeAsc();
        cars.forEach(car -> {
            result.append(String.format("Car make - %s, model - %s", car.getMake(), car.getModel())).append(System.lineSeparator());
            result.append(String.format("\tKilometers - %s", car.getKilometers())).append(System.lineSeparator());
            result.append(String.format("\tRegistered on - %s", car.getRegisteredOn())).append(System.lineSeparator());
            result.append(String.format("\tNumber of pictures - %s", car.getPictures().size())).append(System.lineSeparator());
        });

        return result.toString().trim();
    }
}
