package com.s0qva.application.controller;

import com.s0qva.application.model.Product;
import com.s0qva.application.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getOne(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping("/products")
    public Product save(@Valid @RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String responseMessage =  productService.deleteProduct(id);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
