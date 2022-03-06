package com.s0qva.application.controller;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.service.ProductService;
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
@RequestMapping("/api/v1")
public class ProductController {
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductReadingDto>> getAll() {
        List<ProductReadingDto> products = productService.getAllProducts();

        return ResponseEntity.ok()
                .body(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductReadingDto> getOne(@PathVariable Long id) {
        ProductReadingDto product = productService.getProduct(id);

        return ResponseEntity.ok()
                .body(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Void> save(@Valid @RequestBody ProductCreationDto product) {
        ProductIdDto savedProduct = productService.saveProduct(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedProduct.getId())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductReadingDto> update(@PathVariable Long id, @Valid @RequestBody ProductCreationDto product) {
        ProductReadingDto updatedProduct = productService.updateProduct(id, product);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);

        return ResponseEntity.ok()
                .build();
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
