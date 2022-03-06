package com.s0qva.application.service;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.exception.NoSuchProductException;
import com.s0qva.application.mapper.product.GeneralProductMapper;
import com.s0qva.application.model.Product;
import com.s0qva.application.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private GeneralProductMapper productMapper;

    public List<ProductReadingDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::mapProductToProductReadingDto)
                .collect(Collectors.toList());
    }

    public ProductReadingDto getProduct(Long id) {
        Optional<Product> maybeProduct = productRepository.findById(id);

        return maybeProduct.map(productMapper::mapProductToProductReadingDto)
                .orElseThrow(() -> new NoSuchProductException("There is no product with id = " + id));
    }

    public ProductIdDto saveProduct(ProductCreationDto productCreationDto) {
        Product product = productMapper.mapProductCreationDtoToProduct(productCreationDto);
        Product savedProduct = productRepository.save(product);

        return productMapper.mapProductToProductIdDto(savedProduct);
    }

    public ProductReadingDto updateProduct(Long id, ProductCreationDto productCreationDto) {
        Optional<Product> maybeOldProduct = productRepository.findById(id);
        Product oldProduct = maybeOldProduct.orElseThrow(() -> new NoSuchProductException("There is no product with id = " + id));
        Product newProduct = productMapper.mapProductCreationDtoToProduct(productCreationDto);

        newProduct.getDetails().setId(oldProduct.getDetails().getId());
        oldProduct.setName(newProduct.getName());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.addDetails(newProduct.getDetails());

        Product updatedProduct = productRepository.save(oldProduct);

        return productMapper.mapProductToProductReadingDto(updatedProduct);
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
    public void setProductMapper(GeneralProductMapper productMapper) {
        this.productMapper = productMapper;
    }
}
