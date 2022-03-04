package com.s0qva.application.service;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.exception.NoSuchProductException;
import com.s0qva.application.mapper.Mapper;
import com.s0qva.application.model.Product;
import com.s0qva.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private Mapper<Product, ProductReadingDto> productReadingDtoMapper;
    private Mapper<ProductCreationDto, Product> productMapper;

    public List<ProductReadingDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(productReadingDtoMapper::map)
                .collect(Collectors.toList());
    }

    public ProductReadingDto getProduct(Long id) {
        Optional<Product> maybeProduct = productRepository.findById(id);

        return maybeProduct.map(productReadingDtoMapper::map)
                .orElseThrow(() -> new NoSuchProductException("There is no product with id = " + id));
    }

    public Long saveProduct(ProductCreationDto productCreationDto) {
        Product product = productMapper.map(productCreationDto);
        Product savedProduct = productRepository.save(product);

        return savedProduct.getId();
    }

    public ProductReadingDto updateProduct(Long id, ProductCreationDto newProductDto) {
        Optional<Product> maybeOldProduct = productRepository.findById(id);
        Product newProduct = productMapper.map(newProductDto);

        Product oldProduct = maybeOldProduct.orElseThrow(() -> new NoSuchProductException("There is no product with id = " + id));

        newProduct.getDetails().setId(oldProduct.getDetails().getId());
        oldProduct.setName(newProduct.getName());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.addDetails(newProduct.getDetails());

        Product updatedProduct = productRepository.save(oldProduct);

        return productReadingDtoMapper.map(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Optional<Product> maybeProduct = productRepository.findById(id);

        Product product = maybeProduct.orElseThrow(() -> new NoSuchProductException("There is no product with id = " + id));
        productRepository.delete(product);
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    @Qualifier("productReadingMapper")
    public void setProductReadingDtoMapper(Mapper<Product, ProductReadingDto> productReadingDtoMapper) {
        this.productReadingDtoMapper = productReadingDtoMapper;
    }

    @Autowired
    @Qualifier("productMapper")
    public void setProductMapper(Mapper<ProductCreationDto, Product> productMapper) {
        this.productMapper = productMapper;
    }
}
