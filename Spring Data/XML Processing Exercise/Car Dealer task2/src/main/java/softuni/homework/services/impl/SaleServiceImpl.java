package softuni.homework.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.homework.entities.Car;
import softuni.homework.entities.Customer;
import softuni.homework.entities.Sale;
import softuni.homework.repositories.SaleRepository;
import softuni.homework.services.CarService;
import softuni.homework.services.CustomerService;
import softuni.homework.services.SaleService;

import java.util.Random;

import static softuni.homework.constants.SaleDiscount.DISCOUNT_VALUE_PERCENTAGE;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final Random random;
    private final CarService carService;
    private final CustomerService customerService;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, Random random, CarService carService, CustomerService customerService) {
        this.saleRepository = saleRepository;
        this.random = random;
        this.carService = carService;
        this.customerService = customerService;
    }

    @Override
    public void seedSales() {
        for (int i = 0; i < 100; i++) {

            Sale sale = new Sale();
            double randomDiscount = this.getRandomDiscount();
            sale.setDiscount(randomDiscount);
            Car randomCar = this.carService.getRandomCar();
            sale.setCar(randomCar);
            Customer randomCustomer = this.customerService.getRandomCustomer();
            sale.setCustomer(randomCustomer);

            this.saleRepository.saveAndFlush(sale);
            System.out.println(String.format("%s %s sold to %s! with %.2f/100 discount",
                    randomCar.getMake(), randomCar.getModel(),
                    randomCustomer.getName(),
                    (randomDiscount * 100)));
        }
    }

    private double getRandomDiscount() {
        int randomIndex = random.nextInt(DISCOUNT_VALUE_PERCENTAGE.length);
        return DISCOUNT_VALUE_PERCENTAGE[randomIndex];

    }
}
