package softuni.exam.service;


import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Picture;

import java.io.IOException;
import java.util.List;


public interface PictureService {

    boolean areImported();

    String readPicturesFromFile() throws IOException;
	
	String importPictures() throws IOException;

    Picture getPictureByName(String name);

    List<Picture> getAllPicturesByCar(Car car);
}
