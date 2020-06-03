package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.picture.PictureSeedDTO;
import softuni.exam.models.dtos.picture.PictureStringNODATEdto;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.FileIOUtil;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constats.GlobalFilePaths.PICTURES_FILE_PATH;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final FileIOUtil fileIOUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CarService carService;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, FileIOUtil fileIOUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, CarService carService) {
        this.pictureRepository = pictureRepository;
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.carService = carService;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return this.fileIOUtil.readFileContent(PICTURES_FILE_PATH);
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder info = new StringBuilder();

        PictureStringNODATEdto[] pictureStringNODATEdtos = this.gson.fromJson(new FileReader(PICTURES_FILE_PATH), PictureStringNODATEdto[].class);

        Arrays.stream(pictureStringNODATEdtos).forEach(pictureStringNODATEdto -> {

//            2014-06-10 03:31:39 example
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(pictureStringNODATEdto.getDateAndTime(), formatter);


            PictureSeedDTO pictureSeedDTO = new PictureSeedDTO();
            pictureSeedDTO.setName(pictureStringNODATEdto.getName());
            pictureSeedDTO.setDateAndTime(date);
            pictureSeedDTO.setCar(
                    this.carService.getCarById(pictureStringNODATEdto.getCar()));


            if (isPictureUnique(pictureSeedDTO)) {
                Picture picture = this.modelMapper.map(pictureSeedDTO, Picture.class);
                if (this.validationUtil.isValid(picture)) {
                    Car carById = this.carService.getCarById(pictureSeedDTO.getCar().getId());
                    carById.getPictures().add(picture);
                    picture.setCar(carById);
                    this.pictureRepository.saveAndFlush(picture);
                    info.append(String.format("Successfully import picture - %s", picture.getName()));
                } else {
                    info.append("Invalid picture");
                }


            }
            info.append(System.lineSeparator());

        });
        return info.toString();
    }

    @Override
    public Picture getPictureByName(String name) {
        return this.pictureRepository.getByName(name);
    }


    @Override
    public List<Picture> getAllPicturesByCar(Car car) {

        List<Picture> picturesByCar = this.pictureRepository.findAllByCar_Id(car.getId());
        return picturesByCar;
    }


    private boolean isPictureUnique(PictureSeedDTO pictureSeedDTO) {
        return this.pictureRepository.getByName(pictureSeedDTO.getName()) == null;
    }
}
