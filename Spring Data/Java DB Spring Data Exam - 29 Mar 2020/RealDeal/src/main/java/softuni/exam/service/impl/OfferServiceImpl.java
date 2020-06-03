package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.offer.OfferSeedDTO;
import softuni.exam.models.dtos.offer.OffersSeedRootDTO;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Picture;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.PictureService;
import softuni.exam.service.SellerService;
import softuni.exam.util.FileIOUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static softuni.exam.constats.GlobalFilePaths.OFFERS_FILE_PATH;

@Service
@Transactional
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final FileIOUtil fileIOUtil;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final CarService carService;
    private final SellerService sellerService;
    private final PictureService pictureService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, FileIOUtil fileIOUtil, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser, CarService carService, SellerService sellerService, PictureService pictureService) {
        this.offerRepository = offerRepository;
        this.fileIOUtil = fileIOUtil;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.carService = carService;
        this.sellerService = sellerService;
        this.pictureService = pictureService;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return this.fileIOUtil.readFileContent(OFFERS_FILE_PATH);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder info = new StringBuilder();

        OffersSeedRootDTO offersSeedRootDTO = this.xmlParser.unmarshalFromFile(OFFERS_FILE_PATH, OffersSeedRootDTO.class);

        offersSeedRootDTO.getOffers().forEach(offerSeedDTO -> {
            if (this.validationUtil.isValid(offerSeedDTO)) {

                Offer offer = this.modelMapper.map(offerSeedDTO, Offer.class);

                if (isOfferUniqueByDescriptionAndAddedOn(offer.getDescription(), offer.getAddedOn())) {

                    offer.setSeller(this.sellerService.getSellerById(offerSeedDTO.getSeller().getId()));
                    offer.setCar(this.carService.getCarById(offerSeedDTO.getCar().getId()));

                    offer.setPictures(this.pictureService.getAllPicturesByCar(offer.getCar()));
                    offer.getPictures().
                            forEach(picture -> picture.getOffers().add(offer));

                    this.offerRepository.saveAndFlush(offer);
                    info.append(String.format("Successfully import offer %s - %s",
                            offer.getAddedOn(), offer.getHasGoldStatus()));
                } else {
                    info.append("Invalid offer");
                }
//
            } else {
                info.append("Invalid offer");
            }
            info.append(System.lineSeparator());
        });

        return info.toString();
    }


    private boolean isOfferUniqueByDescriptionAndAddedOn(String description, LocalDateTime addedOn) {
        return this.offerRepository.findByDescriptionAndAddedOn(description, addedOn) == null;
    }
}
