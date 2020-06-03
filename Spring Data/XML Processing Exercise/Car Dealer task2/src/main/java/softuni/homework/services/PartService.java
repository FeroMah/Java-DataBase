package softuni.homework.services;

import softuni.homework.dtos.imports.parts.PartImportRootDTO;
import softuni.homework.entities.Part;

import java.util.List;

public interface PartService {

    void seedParts(PartImportRootDTO partImportRootDTO);

    List<Part> getRandomParts();
}
