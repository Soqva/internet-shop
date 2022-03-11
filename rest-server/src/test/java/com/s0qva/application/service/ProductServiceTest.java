package com.s0qva.application.service;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import com.s0qva.application.exception.NoSuchProductException;
import com.s0qva.application.exception.model.enumeration.DefaultExceptionMessage;
import com.s0qva.application.mapper.product.GeneralProductMapper;
import com.s0qva.application.model.Product;
import com.s0qva.application.model.ProductDetails;
import com.s0qva.application.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({
        MockitoExtension.class
})
class ProductServiceTest {
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private GeneralProductMapper generalProductMapper;


    @BeforeEach
    void setUp() {
        this.productService = new ProductService(productRepository, generalProductMapper);
    }

    @Test
    void itShouldReturnAllRandomProductsAsProductReadingDtoList() {
        List<Product> productsBeforeMapping = List.of(
                new Product(),
                new Product(),
                new Product()
        );
        List<ProductReadingDto> expectedProductsReadingDto = List.of(
                new ProductReadingDto(),
                new ProductReadingDto(),
                new ProductReadingDto()
        );

        when(productRepository.findAll())
                .thenReturn(productsBeforeMapping);
        when(generalProductMapper.mapProductToProductReadingDto(any(Product.class)))
                .thenReturn(new ProductReadingDto());

        List<ProductReadingDto> actualProductsReadingDto = productService.getAllProducts();

        assertAll(() -> {
            assertThat(actualProductsReadingDto.size())
                    .isEqualTo(expectedProductsReadingDto.size());
            assertThat(actualProductsReadingDto)
                    .isEqualTo(expectedProductsReadingDto);
        });

        verify(productRepository, times(1))
                .findAll();
        verify(generalProductMapper, times(expectedProductsReadingDto.size()))
                .mapProductToProductReadingDto(any(Product.class));
    }

    @Test
    void isShouldReturnListWithTwoSpecificProductReadingDto() {
        Long firstProductId = CommonTestValue.EXISTING_PRODUCT_ID;
        Long secondProductId = firstProductId++;
        Product firstProduct = Product.builder()
                .id(firstProductId)
                .build();
        Product secondProduct = Product.builder()
                .id(secondProductId)
                .build();
        ProductReadingDto firstProductReadingDto = ProductReadingDto.builder()
                .id(firstProduct.getId())
                .build();
        ProductReadingDto secondProductReadingDto = ProductReadingDto.builder()
                .id(secondProduct.getId())
                .build();

        List<Product> productsBeforeMapping = List.of(
                firstProduct,
                secondProduct
        );
        List<ProductReadingDto> expectedProductsReadingDto = List.of(
                firstProductReadingDto,
                secondProductReadingDto
        );

        when(productRepository.findAll())
                .thenReturn(productsBeforeMapping);
        when(generalProductMapper.mapProductToProductReadingDto(firstProduct))
                .thenReturn(firstProductReadingDto);
        when(generalProductMapper.mapProductToProductReadingDto(secondProduct))
                .thenReturn(secondProductReadingDto);

        List<ProductReadingDto> actualProductsReadingDto = productService.getAllProducts();

        assertAll(() -> {
            assertThat(actualProductsReadingDto.size())
                    .isEqualTo(expectedProductsReadingDto.size());
            assertThat(actualProductsReadingDto)
                    .isEqualTo(expectedProductsReadingDto);
        });

        verify(productRepository, times(1))
                .findAll();
        verify(generalProductMapper, times(2))
                .mapProductToProductReadingDto(any(Product.class));
        verify(generalProductMapper, times(1))
                .mapProductToProductReadingDto(firstProduct);
        verify(generalProductMapper, times(1))
                .mapProductToProductReadingDto(secondProduct);
    }

    @Test
    void itShouldReturnOneProductAsProductReadingDtoByItsId() {
        Long productId = CommonTestValue.EXISTING_PRODUCT_ID;
        Product productBeforeMapping = Product.builder()
                .id(productId)
                .build();
        ProductReadingDto expectedProductReadingDto = ProductReadingDto.builder()
                .id(productId)
                .build();

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(productBeforeMapping));
        when(generalProductMapper.mapProductToProductReadingDto(productBeforeMapping))
                .thenReturn(expectedProductReadingDto);

        ProductReadingDto actualProductReadingDto = productService.getProduct(productId);

        assertThat(actualProductReadingDto)
                .isEqualTo(expectedProductReadingDto);

        verify(productRepository, times(1))
                .findById(productId);
        verify(generalProductMapper, times(1))
                .mapProductToProductReadingDto(productBeforeMapping);
    }

    @Test
    void itShouldThrowsNoSuchProductExceptionWithSpecificMessageWhenProductDoesntExistWithSentId() {
        Long nonExistentProductId = CommonTestValue.NON_EXISTENT_PRODUCT_ID;
        Optional<Product> nonExistentProduct = Optional.empty();
        String expectedExceptionMessage = DefaultExceptionMessage.NO_SUCH_PRODUCT_WITH_ID.getMessage() + nonExistentProductId;

        when(productRepository.findById(nonExistentProductId))
                .thenReturn(nonExistentProduct);

        NoSuchProductException actualException = assertThrows(NoSuchProductException.class,
                () -> productService.getProduct(nonExistentProductId));

        assertThat(actualException)
                .isOfAnyClassIn(NoSuchProductException.class)
                .hasMessage(expectedExceptionMessage);

        verify(productRepository, times(1))
                .findById(nonExistentProductId);
    }

    @Test
    void itShouldReturnSavedProductIdWhenSendProductCreationDto() {
        String productName = "productCreationDto";
        Long savedProductId = CommonTestValue.EXISTING_PRODUCT_ID;
        ProductCreationDto productCreationDto = ProductCreationDto.builder()
                .name(productName)
                .build();
        Product unsavedProductAfterMapping = Product.builder()
                .name(productName)
                .build();
        Product savedProduct = Product.builder()
                .id(savedProductId)
                .name(productName)
                .build();
        ProductIdDto expectedProductIdDto = ProductIdDto.builder()
                .id(savedProductId)
                .build();

        when(generalProductMapper.mapProductCreationDtoToProduct(productCreationDto))
                .thenReturn(unsavedProductAfterMapping);
        when(productRepository.save(unsavedProductAfterMapping))
                .thenReturn(savedProduct);
        when(generalProductMapper.mapProductToProductIdDto(savedProduct))
                .thenReturn(expectedProductIdDto);

        ProductIdDto actualProductIdDto = productService.saveProduct(productCreationDto);

        assertThat(actualProductIdDto)
                .isEqualTo(expectedProductIdDto);

        verify(generalProductMapper, times(1))
                .mapProductCreationDtoToProduct(productCreationDto);
        verify(productRepository, times(1))
                .save(unsavedProductAfterMapping);
        verify(generalProductMapper, times(1))
                .mapProductToProductIdDto(savedProduct);
    }

    @Test
    void itShouldReturnProductReadingDtoContainsUpdatedProductInfoByProductId() {
        Long productId = CommonTestValue.EXISTING_PRODUCT_ID;
        Long productDetailsId = CommonTestValue.EXISTING_PRODUCT_DETAILS_ID;
        String newProductName = CommonTestValue.PRODUCT_NAME;
        ProductCreationDto newProductCreationDto = ProductCreationDto.builder()
                .name(newProductName)
                .build();
        ProductDetails newProductDetails = ProductDetails.builder()
                .build();
        Product newProduct = Product.builder()
                .name(newProductName)
                .details(newProductDetails)
                .build();
        ProductDetails oldProductDetails = ProductDetails.builder()
                .id(productDetailsId)
                .build();
        Product oldProduct = Product.builder()
                .id(productId)
                .details(oldProductDetails)
                .build();
        Product updatedProduct = Product.builder()
                .id(productId)
                .name(newProductName)
                .details(newProductDetails)
                .build();
        ProductDetailsReadingDto productDetailsReadingDto = ProductDetailsReadingDto.builder()
                .id(productDetailsId)
                .build();
        ProductReadingDto expectedProductReadingDto = ProductReadingDto.builder()
                .id(productId)
                .name(newProductName)
                .details(productDetailsReadingDto)
                .build();

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(oldProduct));
        when(generalProductMapper.mapProductCreationDtoToProduct(newProductCreationDto))
                .thenReturn(newProduct);
        when(productRepository.save(oldProduct))
                .thenReturn(updatedProduct);
        when(generalProductMapper.mapProductToProductReadingDto(updatedProduct))
                .thenReturn(expectedProductReadingDto);

        ProductReadingDto actualProductReadingDto = productService.updateProduct(productId, newProductCreationDto);

        assertThat(actualProductReadingDto)
                .isEqualTo(expectedProductReadingDto);

        verify(productRepository, times(1))
                .findById(productId);
        verify(productRepository, times(1))
                .save(oldProduct);
        verify(generalProductMapper, times(1))
                .mapProductCreationDtoToProduct(newProductCreationDto);
        verify(generalProductMapper, times(1))
                .mapProductToProductReadingDto(updatedProduct);
    }

    @Test
    void itShouldDeleteProductByItsId() {
        Long productId = CommonTestValue.EXISTING_PRODUCT_ID;
        Product product = Product.builder()
                .id(productId)
                .build();

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));
        doNothing()
                .when(productRepository)
                .delete(product);

        productService.deleteProduct(productId);

        verify(productRepository, times(1))
                .findById(productId);
        verify(productRepository, times(1))
                .delete(product);
    }

    private static class CommonTestValue {
        private static final Long EXISTING_PRODUCT_ID = 1L;
        private static final Long NON_EXISTENT_PRODUCT_ID = -1L;
        public static final Long EXISTING_PRODUCT_DETAILS_ID = 1L;
        public static final String PRODUCT_NAME = "PRODUCT_NAME";
    }
}
