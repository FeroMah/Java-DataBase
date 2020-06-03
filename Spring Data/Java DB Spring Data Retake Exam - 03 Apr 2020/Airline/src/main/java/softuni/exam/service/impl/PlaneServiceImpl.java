package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.plane.PlaneSeedRootDTO;
import softuni.exam.models.entities.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.FileIOUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

import static softuni.exam.constants.GlobalFilePaths.PLANES_FILE_PATH;

@Service
public class PlaneServiceImpl implements PlaneService {
    private final PlaneRepository planeRepository;
    private final FileIOUtil fileIOUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaneServiceImpl(PlaneRepository planeRepository, FileIOUtil fileIOUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.planeRepository = planeRepository;
        this.fileIOUtil = fileIOUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() {
        try {
            return
                    this.fileIOUtil.readFileContent(PLANES_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String importPlanes() {
        StringBuilder info = new StringBuilder();

        try {
            PlaneSeedRootDTO planeSeedRootDTO = this.xmlParser.unmarshalFromFile(PLANES_FILE_PATH, PlaneSeedRootDTO.class);

            planeSeedRootDTO.getPlanes().forEach(planeSeedDTO -> {

                if (this.validationUtil.isValid(planeSeedDTO)) {
                    Plane plane = this.modelMapper.map(planeSeedDTO, Plane.class);

                    if (this.planeRepository.getByRegisterNumber(plane.getRegisterNumber()) == null) {

                        this.planeRepository.saveAndFlush(plane);

                        info.append(String.format("Successfully imported Plane %s", plane.getRegisterNumber()));
                    } else {
                        info.append("Invalid Plane");
                    }

                } else {
                    info.append("Invalid Plane");
                }
                info.append(System.lineSeparator());
            });


        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return info.toString();
    }

    @Override
    public Plane getPlaneByRegisterNumber(String registerNumber) {
        return this.planeRepository.getByRegisterNumber(registerNumber);
    }
}
