package com.example.billingservice2.web;

import com.example.billingservice2.entities.Bill;
import com.example.billingservice2.feign.CustomerRestClient;
import com.example.billingservice2.feign.ProductRestClient;
import com.example.billingservice2.models.Customer;
import com.example.billingservice2.models.Product;
import com.example.billingservice2.repositories.BillRepository;
import com.example.billingservice2.repositories.ProductItemRepository;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public BillingRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping(path = "fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill=billRepository.findById(id).get();
        Customer customer=customerRestClient.getCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi->{
            Product product=productRestClient.getProductById(pi.getProductID());
            pi.setProduct(product);
        });
        return bill;
    }

}
