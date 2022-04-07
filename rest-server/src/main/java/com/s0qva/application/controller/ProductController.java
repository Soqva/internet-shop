package com.s0qva.application.controller;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductReadingDto>> getAll() {
        log.info("Received a request for getting all products.");
        List<ProductReadingDto> products = productService.getAllProducts();

        log.info("Sending a list of products: {}. The response status is OK", products);
        return ResponseEntity.ok()
                .body(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductReadingDto> getOne(@PathVariable Long id) {
        log.info("Received a request for getting a product by its id = {}.", id);
        ProductReadingDto product = productService.getProduct(id);

        log.info("Sending a product: {}. The response status is OK", product);
        return ResponseEntity.ok()
                .body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Void> save(@Valid @RequestBody ProductCreationDto product) {
        log.info("Received a request for saving a product. ProductCreationDto: {}.", product);
        ProductIdDto savedProduct = productService.saveProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        log.info("Nothing to send. ProductIdDto savedProduct {}. URI location: {}. The response status is CREATED",
                savedProduct,
                location);
        return ResponseEntity.created(location)
                .build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductReadingDto> update(@PathVariable Long id, @Valid @RequestBody ProductCreationDto product) {
        log.info("Received a request for updating a product. Product's Id: {}. ProductCreationDto: {}.", id, product);
        ProductReadingDto updatedProduct = productService.updateProduct(id, product);

        log.info("Sending a product: {}. The response status is OK", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Received a request for deleting a product. Product's Id: {}.", id);
        productService.deleteProduct(id);

        log.info("Nothing to send. The response status is OK");
        return ResponseEntity.ok()
                .build();
    }
}
