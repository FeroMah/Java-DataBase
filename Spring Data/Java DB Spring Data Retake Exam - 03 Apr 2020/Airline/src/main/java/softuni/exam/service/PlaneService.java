package softuni.exam.service;


import softuni.exam.models.entities.Plane;

//ToDo - Before start App implement this Service and set areImported to return false
public interface PlaneService {

    boolean areImported();

    String readPlanesFileContent() ;
	
	String importPlanes();

	Plane getPlaneByRegisterNumber (String registerNumber);

}
