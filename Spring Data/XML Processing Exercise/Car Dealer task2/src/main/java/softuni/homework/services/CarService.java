package softuni.homework.services;

import softuni.homework.dtos.exports.cars.CarExportRootDTO;
import softuni.homework.dtos.imports.cars.CarImportRootDTO;
import softuni.homework.entities.Car;

public interface CarService {

    void seedCars(CarImportRootDTO carImportRootDTO);

    Car getRandomCar();

    CarExportRootDTO getAllByMakerToyota();
}
