package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.ticket.TicketSeedRootDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.FileIOUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

import static softuni.exam.constants.GlobalFilePaths.*;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final FileIOUtil fileIOUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final PlaneService planeService;
    private final PassengerService passengerService;
    private final TownService townService;
    private final ValidationUtil validationUtil;


    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, FileIOUtil fileIOUtil, XmlParser xmlParser, ModelMapper modelMapper, PlaneService planeService, PassengerService passengerService, TownService townService, ValidationUtil validationUtil) {
        this.ticketRepository = ticketRepository;
        this.fileIOUtil = fileIOUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.planeService = planeService;
        this.passengerService = passengerService;
        this.townService = townService;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() {
        try {
            return
                    this.fileIOUtil.readFileContent(TICKETS_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String importTickets() {
        StringBuilder info = new StringBuilder();

        try {
            TicketSeedRootDTO ticketSeedRootDTO = this.xmlParser.unmarshalFromFile(TICKETS_FILE_PATH, TicketSeedRootDTO.class);

            ticketSeedRootDTO.getTickets().forEach(ticketSeedDTO -> {

                Ticket ticket = this.modelMapper.map(ticketSeedDTO, Ticket.class);
                System.out.println();
//                TODO: add plane , passenger, toTown, formTown

//                get Plane
                Plane plane = this.planeService.getPlaneByRegisterNumber(ticketSeedDTO.getPlane().getRegisterNumber());
                ticket.setPlane(plane);

//                get passenger
                Passenger passenger = this.passengerService.getPassengerByEmailAddress(ticketSeedDTO.getPassenger().getEmail());
                ticket.setPassenger(passenger);


//                get toTown
                Town toTown = this.townService.getTownByName(ticketSeedDTO.getToTown().getName());
                ticket.setToTown(toTown);

//                get fromTown
                Town fromTown = this.townService.getTownByName(ticketSeedDTO.getFromTown().getName());
                ticket.setFromTown(fromTown);

                if (this.validationUtil.isValid(ticket)) {

                    if (this.ticketRepository.getBySerialNumber(ticket.getSerialNumber()) == null) {

                        this.ticketRepository.saveAndFlush(ticket);
                        info.append(String.format("Successfully imported Ticket %s - %s",
                                ticket.getFromTown().getName(),
                                ticket.getToTown().getName()));

//                        add ticket to passenger

//                    Ticket savedInDB  =  this.ticketRepository.getBySerialNumber(ticket.getSerialNumber());
//                        passenger.getTickets().add(savedInDB);

                    } else {
                        info.append("Invalid Ticket");
                    }
                } else {
                    info.append("Invalid Ticket");
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
}
