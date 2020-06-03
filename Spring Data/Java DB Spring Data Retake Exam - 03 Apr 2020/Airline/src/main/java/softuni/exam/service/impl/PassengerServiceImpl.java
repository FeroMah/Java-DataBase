package softuni.exam.service.impl;


import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.passenger.PassengerSeedDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.TownService;
import softuni.exam.util.FileIOUtil;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static softuni.exam.constants.GlobalFilePaths.PASSENGERS_FILE_PATH;

@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final FileIOUtil fileIOUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownService townService;


    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, FileIOUtil fileIOUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, TownService townService) {
        this.passengerRepository = passengerRepository;
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() {
        try {
            return
                    this.fileIOUtil.readFileContent(PASSENGERS_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String importPassengers() {
        StringBuilder info = new StringBuilder();

        try {
            PassengerSeedDTO[] passengerSeedDTOS = this.gson.fromJson(new FileReader(PASSENGERS_FILE_PATH), PassengerSeedDTO[].class);

            Arrays.stream(passengerSeedDTOS).forEach(passengerSeedDTO -> {
                if (this.validationUtil.isValid(passengerSeedDTO)) {

                    Passenger passenger = this.modelMapper.map(passengerSeedDTO, Passenger.class);
                    Town townByName = this.townService.getTownByName(passengerSeedDTO.getTown());
                    passenger.setTown(townByName);

                    if (this.passengerRepository.getByEmail(passenger.getEmail()) == null) {

                        this.passengerRepository.saveAndFlush(passenger);

                        info.append(String.format("Successfully imported Passenger %s - %s",
                                passenger.getLastName(),
                                passenger.getEmail()));

                    } else {
                        info.append("Invalid Passenger");
                    }

                } else {
                    info.append("Invalid Passenger");
                }
                info.append(System.lineSeparator());
            });


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return info.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
//       TODO
        StringBuilder result = new StringBuilder();
        List<Passenger> passengerList = this.passengerRepository.getAllOrderedByTicketsSizeDecsAndEmailAsc();

        passengerList.forEach(passenger -> {
            result.append(String.format("Passenger %s %s", passenger.getFirstName(), passenger.getLastName())).append(System.lineSeparator());
            result.append(String.format("\tEmail - %s", passenger.getEmail())).append(System.lineSeparator());
            result.append(String.format("\tPhone - %s", passenger.getPhoneNumber())).append(System.lineSeparator());
            result.append(String.format("\tNumber of tickets - %d", passenger.getTickets().size())).append(System.lineSeparator());
        });

        return result.toString().trim();
    }

    @Override
    public Passenger getPassengerByEmailAddress(String email) {
        return this.passengerRepository.getByEmail(email);
    }
}
