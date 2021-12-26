package com.example.billingservice2.feign;

import com.example.billingservice2.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.ForeignKey;
@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductRestClient {
    @GetMapping(path = "/products/{id}")
     Product getProductById(@PathVariable(name = "id") Long id);
    @GetMapping(path = "/products")
    PagedModel<Product> pageProducts();


}
