package com.example.billingservice2;

import com.example.billingservice2.entities.Bill;
import com.example.billingservice2.entities.ProductItem;
import com.example.billingservice2.feign.CustomerRestClient;
import com.example.billingservice2.feign.ProductRestClient;
import com.example.billingservice2.models.Customer;
import com.example.billingservice2.models.Product;
import com.example.billingservice2.repositories.BillRepository;
import com.example.billingservice2.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingService2Application {

    public static void main(String[] args) {

        SpringApplication.run(BillingService2Application.class, args);
    }
    @Bean
        CommandLineRunner start(BillRepository billRepository,
                                CustomerRestClient customerRestClient,
                                ProductRestClient productRestClient,
                                ProductItemRepository productItemRepository){
        return args -> {
            Customer customer=customerRestClient.getCustomerById(1L);
            Bill bill1=billRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
            PagedModel<Product> productPagedModel=productRestClient.pageProducts();
            productPagedModel.forEach(p->{
                ProductItem productItem=new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+new Random().nextInt(100));
                productItem.setBill(bill1);
                productItem.setProductID(p.getId());
                productItemRepository.save(productItem);
            });

        };
    }

}
