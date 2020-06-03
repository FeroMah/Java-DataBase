package softuni.exam.service;


import softuni.exam.models.entities.Town;

//ToDo - Before start App implement this Service and set areImported to return false
public interface TownService {

    boolean areImported();

    String readTownsFileContent() ;
	
	String importTowns() ;

	Town  getTownByName (String name);
}
