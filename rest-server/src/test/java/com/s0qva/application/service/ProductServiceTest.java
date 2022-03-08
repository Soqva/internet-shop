package com.s0qva.application.service;

import com.s0qva.application.dto.product.ProductCreationDto;
import com.s0qva.application.dto.product.ProductIdDto;
import com.s0qva.application.dto.product.ProductReadingDto;
import com.s0qva.application.dto.product.detail.ProductDetailsCreationDto;
import com.s0qva.application.dto.product.detail.ProductDetailsReadingDto;
import com.s0qva.application.exception.NoSuchProductException;
import com.s0qva.application.exception.model.enumeration.DefaultExceptionMessage;
import com.s0qva.application.mapper.product.GeneralProductMapper;
import com.s0qva.application.model.Product;
import com.s0qva.application.model.ProductDetails;
import com.s0qva.application.model.enumeration.Country;
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
        Product firstProduct = CommonTestProductEntity.FAKE_PRODUCT_WITH_ID_1;
        Product secondProduct = CommonTestProductEntity.FAKE_PRODUCT_WITH_ID_2;
        ProductReadingDto firstProductReadingDto = CommonTestProductEntity.FAKE_PRODUCT_READING_DTO_WITH_ID_1;
        ProductReadingDto secondProductReadingDto = CommonTestProductEntity.FAKE_PRODUCT_READING_DTO_WITH_ID_2;

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
        Long possibleProductId = CommonTestProductEntity.POSSIBLE_PRODUCT_ID;
        Product productBeforeMapping = CommonTestProductEntity.FAKE_PRODUCT_WITH_ID_1;
        ProductReadingDto expectedProductReadingDto = CommonTestProductEntity.FAKE_PRODUCT_READING_DTO_WITH_ID_1;

        when(productRepository.findById(possibleProductId))
                .thenReturn(Optional.of(productBeforeMapping));
        when(generalProductMapper.mapProductToProductReadingDto(productBeforeMapping))
                .thenReturn(expectedProductReadingDto);

        ProductReadingDto actualProductReadingDto = productService.getProduct(possibleProductId);

        assertThat(actualProductReadingDto)
                .isEqualTo(expectedProductReadingDto);

        verify(productRepository, times(1))
                .findById(possibleProductId);
        verify(generalProductMapper, times(1))
                .mapProductToProductReadingDto(productBeforeMapping);
    }

    @Test
    void itShouldThrowsNoSuchProductExceptionWithSpecificMessageWhenProductDoesntExistWithSentId() {
        Optional<Product> nonExistentProduct = CommonTestProductEntity.FAKE_NON_EXISTENT_PRODUCT;
        Long nonExistentProductId = CommonTestProductEntity.NON_EXISTENT_PRODUCT_ID;

        when(productRepository.findById(nonExistentProductId))
                .thenReturn(nonExistentProduct);

        NoSuchProductException actualException = assertThrows(NoSuchProductException.class,
                () -> productService.getProduct(nonExistentProductId));

        assertThat(actualException)
                .isOfAnyClassIn(NoSuchProductException.class)
                .hasMessage(DefaultExceptionMessage.NO_SUCH_PRODUCT_WITH_ID.getMessage() + nonExistentProductId);

        verify(productRepository, times(1))
                .findById(nonExistentProductId);
    }

    @Test
    void itShouldReturnSavedProductIdWhenSendProductCreationDto() {
        ProductCreationDto productCreationDto = CommonTestProductEntity.FAKE_PRODUCT_CREATION_DTO;
        Product unsavedProduct = CommonTestProductEntity.FAKE_PRODUCT_WITHOUT_ID;
        Product savedProduct = CommonTestProductEntity.FAKE_PRODUCT_WITH_ID_1;
        ProductIdDto expectedProductIdDto = CommonTestProductEntity.FAKE_PRODUCT_ID_DTO_WITH_ID_1;

        when(generalProductMapper.mapProductCreationDtoToProduct(productCreationDto))
                .thenReturn(unsavedProduct);
        when(productRepository.save(unsavedProduct))
                .thenReturn(savedProduct);
        when(generalProductMapper.mapProductToProductIdDto(savedProduct))
                .thenReturn(expectedProductIdDto);

        ProductIdDto actualProductIdDto = productService.saveProduct(productCreationDto);

        assertThat(actualProductIdDto)
                .isEqualTo(expectedProductIdDto);

        verify(generalProductMapper, times(1))
                .mapProductCreationDtoToProduct(productCreationDto);
        verify(productRepository, times(1))
                .save(unsavedProduct);
        verify(generalProductMapper, times(1))
                .mapProductToProductIdDto(savedProduct);
    }

    @Test
    void itShouldReplaceOldProductWithNewProductByOldProductIdAndReturnProductReadingDto() {
        Long possibleOldProductId = CommonTestProductEntity.POSSIBLE_PRODUCT_ID;
        ProductCreationDto newProductCreationDto = CommonTestProductEntity.FAKE_PRODUCT_CREATION_DTO;
        Product oldProduct = CommonTestProductEntity.FAKE_PRODUCT_WITH_ID_1;
        oldProduct.addDetails(CommonTestProductEntity.PRODUCT_DETAILS_WITH_ID);
        Product newProduct = CommonTestProductEntity.FAKE_PRODUCT_WITHOUT_ID;
        newProduct.addDetails(CommonTestProductEntity.PRODUCT_DETAILS_WITHOUT_ID);
        Product updatedProduct = Product.builder()
                .id(oldProduct.getId())
                .name(newProduct.getName())
                .price(newProduct.getPrice())
                .build();
        updatedProduct.addDetails(ProductDetails.builder()
                .id(oldProduct.getDetails().getId())
                .description(newProduct.getDetails().getDescription())
                .madeIn(newProduct.getDetails().getMadeIn())
                .build());
        ProductReadingDto expectedProductReadingDto = ProductReadingDto.builder()
                .id(updatedProduct.getId())
                .name(updatedProduct.getName())
                .price(updatedProduct.getPrice())
                .details(ProductDetailsReadingDto.builder()
                        .id(updatedProduct.getDetails().getId())
                        .description(updatedProduct.getDetails().getDescription())
                        .build())
                .build();

        when(productRepository.findById(possibleOldProductId))
                .thenReturn(Optional.of(oldProduct));
        when(generalProductMapper.mapProductCreationDtoToProduct(newProductCreationDto))
                .thenReturn(newProduct);
        when(productRepository.save(oldProduct))
                .thenReturn(updatedProduct);
        when(generalProductMapper.mapProductToProductReadingDto(updatedProduct))
                .thenReturn(expectedProductReadingDto);

        ProductReadingDto actualProductReadingDto = productService.updateProduct(possibleOldProductId, newProductCreationDto);

        assertThat(actualProductReadingDto)
                .isEqualTo(expectedProductReadingDto);

        verify(productRepository, times(1))
                .findById(possibleOldProductId);
        verify(productRepository, times(1))
                .save(oldProduct);
        verify(generalProductMapper, times(1))
                .mapProductCreationDtoToProduct(newProductCreationDto);
        verify(generalProductMapper, times(1))
                .mapProductToProductReadingDto(updatedProduct);
    }

    @Test
    void itShouldDeleteProductByItsId() {
        Product product = CommonTestProductEntity.FAKE_PRODUCT_WITH_ID_1;
        Long possibleProductId = CommonTestProductEntity.POSSIBLE_PRODUCT_ID;

        when(productRepository.findById(possibleProductId))
                .thenReturn(Optional.of(product));
        doNothing()
                .when(productRepository)
                .delete(product);

        productService.deleteProduct(possibleProductId);

        verify(productRepository, times(1))
                .findById(possibleProductId);
        verify(productRepository, times(1))
                .delete(product);
    }

    private static class CommonTestProductEntity {
        private static final Long NON_EXISTENT_PRODUCT_ID = -1L;
        private static final Long POSSIBLE_PRODUCT_ID = 1L;
        private static final Optional<Product> FAKE_NON_EXISTENT_PRODUCT = Optional.empty();
        private static final Product FAKE_EMPTY_PRODUCT = Product.builder()
                .build();
        private static final Product FAKE_PRODUCT_WITH_ID_1 = Product.builder()
                .id(1L)
                .name("FAKE_PRODUCT_WITH_ID")
                .price(100.100)
                .details(new ProductDetails())
                .build();
        private static final Product FAKE_PRODUCT_WITH_ID_2 = Product.builder()
                .id(2L)
                .name("FAKE_PRODUCT_WITH_ID")
                .price(100.100)
                .details(new ProductDetails())
                .build();
        private static final Product FAKE_PRODUCT_WITHOUT_ID = Product.builder()
                .name("FAKE_PRODUCT_WITHOUT_ID")
                .price(100.100)
                .details(new ProductDetails())
                .build();
        private static final ProductDetails PRODUCT_DETAILS_WITH_ID = ProductDetails.builder()
                .id(1L)
                .description("PRODUCT_DETAILS_DESCRIPTION")
                .madeIn(Country.RUSSIA)
                .build();
        private static final ProductDetails PRODUCT_DETAILS_WITHOUT_ID = ProductDetails.builder()
                .description("PRODUCT_DETAILS_WITHOUT_ID")
                .madeIn(Country.RUSSIA)
                .build();
        private static final ProductCreationDto FAKE_PRODUCT_CREATION_DTO = ProductCreationDto.builder()
                .name("FAKE_PRODUCT_CREATION_DTO")
                .price(100.100)
                .details(new ProductDetailsCreationDto())
                .build();
        private static final ProductReadingDto FAKE_PRODUCT_READING_DTO_WITH_ID_1 = ProductReadingDto.builder()
                .id(1L)
                .name("FAKE_PRODUCT_READING_DTO")
                .price(100.100)
                .details(new ProductDetailsReadingDto())
                .build();
        private static final ProductReadingDto FAKE_PRODUCT_READING_DTO_WITH_ID_2 = ProductReadingDto.builder()
                .id(2L)
                .name("FAKE_PRODUCT_READING_DTO")
                .price(100.100)
                .details(new ProductDetailsReadingDto())
                .build();
        private static final ProductIdDto FAKE_PRODUCT_ID_DTO_WITH_ID_1 = ProductIdDto.builder()
                .id(1L)
                .build();
    }
}
