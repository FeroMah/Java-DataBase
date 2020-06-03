package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.town.TownSeedDTO;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.FileIOUtil;
import softuni.exam.util.ValidationUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static softuni.exam.constants.GlobalFilePaths.*;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final FileIOUtil fileIOUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileIOUtil fileIOUtil, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.fileIOUtil = fileIOUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() {
        try {
            return
                    this.fileIOUtil.readFileContent(TOWNS_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String importTowns() {
        StringBuilder info = new StringBuilder();

        try {
            TownSeedDTO[] townSeedDTOS = this.gson.fromJson(new FileReader(TOWNS_FILE_PATH), TownSeedDTO[].class);

            Arrays.stream(townSeedDTOS).forEach(townSeedDTO -> {

                if (this.validationUtil.isValid(townSeedDTO)) {

                    Town town = this.modelMapper.map(townSeedDTO, Town.class);

                    if (this.townRepository.getByName(town.getName()) == null) {
                        this.townRepository.saveAndFlush(town);

                        info.append(String.format("Successfully imported Town %s - %d",
                                town.getName(), town.getPopulation()));
                    } else {
                        info.append("Invalid town");
                    }

                } else {
                    info.append("Invalid town");
                }
                info.append(System.lineSeparator());

            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return info.toString();
    }

    @Override
    public Town getTownByName(String name) {
        return this.townRepository.getByName(name);
    }
}
