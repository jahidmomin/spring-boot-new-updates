package com.app.springsecuritypoc.controller;

import com.app.springsecuritypoc.dto.Product;
import com.app.springsecuritypoc.entity.UserInfo;
import com.app.springsecuritypoc.entity.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable() int id) {
        Product product = new Product();
        product.setId(1);
        product.setName("Apple");
        return product;
    }

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/new")
    public UserInfo create(@RequestBody UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userInfoRepository.save(userInfo);
    }


}
