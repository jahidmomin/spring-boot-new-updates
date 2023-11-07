package com.app.springsecuritypoc.controller;

import com.app.springsecuritypoc.dto.Product;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome";
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllProducts() {
        Product product = new Product();
        product.setId(1);
        product.setName("Apple");
        Product product1 = new Product();
        product1.setId(2);
        product1.setName("Mango");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product);

        return products;
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable() int id) {
        Product product = new Product();
        product.setId(1);
        product.setName("Apple");
        return product;
    }



}
