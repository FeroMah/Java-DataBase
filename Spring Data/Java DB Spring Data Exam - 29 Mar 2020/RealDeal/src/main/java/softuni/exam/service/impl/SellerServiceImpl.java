package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constats.GlobalFilePaths;
import softuni.exam.models.dtos.saller.SellerSeedDTO;
import softuni.exam.models.dtos.saller.SellersSeedRootDTO;
import softuni.exam.models.entities.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.FileIOUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

import static softuni.exam.constats.GlobalFilePaths.SELLERS_FILE_PATH;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final FileIOUtil fileIOUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public SellerServiceImpl(SellerRepository sellerRepository, FileIOUtil fileIOUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.sellerRepository = sellerRepository;
        this.fileIOUtil = fileIOUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return this.fileIOUtil.readFileContent(SELLERS_FILE_PATH);
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder info = new StringBuilder();

        SellersSeedRootDTO sellersSeedRootDTO = this.xmlParser.unmarshalFromFile(SELLERS_FILE_PATH, SellersSeedRootDTO.class);


        sellersSeedRootDTO.getSellers().forEach(sellerSeedDTO -> {

            if (this.validationUtil.isValid(sellerSeedDTO)
                    && hasSellerUniqueEmailAddress(sellerSeedDTO)) {

                Seller seller = this.modelMapper.map(sellerSeedDTO, Seller.class);
                this.sellerRepository.saveAndFlush(seller);

                info.append(String.format("Successfully import seller %s - %s",
                        seller.getLastName(), seller.getEmail()));
            } else {
                info.append("Invalid seller");
            }
            info.append(System.lineSeparator());
        });
        return info.toString();
    }

    @Override
    public Seller getSellerById(Long id) {
        Seller sellerById = this.sellerRepository.getOne(id);
        return sellerById;
    }

    private boolean hasSellerUniqueEmailAddress(SellerSeedDTO seller) {
        return this.sellerRepository.findByEmail(seller.getEmail()) == null;
    }

}
