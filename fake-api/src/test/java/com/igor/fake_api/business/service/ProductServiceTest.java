package com.igor.fake_api.business.service;

import com.igor.fake_api.apiv1.dto.ProductDTO;
import com.igor.fake_api.factory.ProductFactory;
import com.igor.fake_api.infrastructure.entities.Product;
import com.igor.fake_api.infrastructure.exceptions.ProductAlreadyExistsException;
import com.igor.fake_api.infrastructure.exceptions.ProductUnexistException;
import com.igor.fake_api.infrastructure.repository.ProductRepository;
import com.igor.fake_api.utils.ProductUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductUtils productUtils;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should save a product correctly")
    public void saveProduct_Case1() {

        // Arrange
        ProductDTO newProduct = ProductFactory.buildProductDto();
        Product productEntity = ProductUtils.dtoToEntity(newProduct);

        when(productRepository.existsByTitle(newProduct.getTitle())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(productEntity);

        // Act
        productService.saveProduct(newProduct);

        // Assert
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository, times(1)).save(productCaptor.capture());
        Product capturedProduct = productCaptor.getValue();

        assertEquals(productEntity.getTitle(), capturedProduct.getTitle());
        verify(productRepository, times(1)).existsByTitle(newProduct.getTitle());
    }

    @Test
    @DisplayName("Should throw an exception when try to save a product who already exists")
    public void saveProduct_Case2() {

        ProductDTO product = ProductFactory.buildProductDto();

        when(productRepository.existsByTitle(product.getTitle())).thenReturn(true);

        ProductAlreadyExistsException exception =
                assertThrows(
                        ProductAlreadyExistsException.class,
                        () -> productService.saveProduct(product)
                );


        assertEquals(exception.getMessage(), "This product is already registered");

    }

    @Test
    @DisplayName("Should return all products")
    public void findAlLProducts() {

        var allProducts = ProductFactory.buildProductsList();

        when(productRepository.findAll()).thenReturn(allProducts);

        var response = productService.findAllProducts();

        assertEquals(allProducts.size(), response.size());
        assertEquals(allProducts.getFirst().getTitle(), response.getFirst().getTitle());
        assertEquals(allProducts.getLast().getTitle(), response.getLast().getTitle());
    }

    @Test
    @DisplayName("Should return the product when it exists by its title")
    public void findByTitle_Case1() {

        // Arrange
        Product product = ProductFactory.buildProduct();

        when(productRepository.findByTitle(product.getTitle())).thenReturn(product);

        // Act
        var response = productService.findByTitle(product.getTitle());

        // Assert
        assertNotNull(response);
        verify(productRepository, times(1)).findByTitle(product.getTitle());

        assertEquals(response.getTitle(), product.getTitle());
        assertEquals(response.getPrice(), product.getPrice());
        assertEquals(response.getDescription(), product.getDescription());
        assertEquals(response.getImage(), product.getImage());
        assertEquals(response.getCategory(), product.getCategory());

    }

    @Test
    @DisplayName("Should throw an exception when the product does not exist")
    public void findByTitle_Case2() {

        String nonExistentTitle = "Non Existent Title";
        when(productRepository.findByTitle(anyString())).thenReturn(null);

        ProductUnexistException exception =
                assertThrows(
                        ProductUnexistException.class,
                        () -> productService.findByTitle(nonExistentTitle)
                );


        assertEquals(exception.getMessage(),"This product does not exist" );
        verify(productRepository, times(1)).findByTitle(nonExistentTitle);

    }

    @Test
    @DisplayName("Should delete a product when it exists by its title")
    public void deleteProduct_Case1() {

        Product product = ProductFactory.buildProduct();
        when(productRepository.findByTitle(product.getTitle()))
                .thenReturn(product);

        productService.deleteProduct(product.getTitle());

        verify(productRepository, times(1)).delete(product);
    }

    @Test
    @DisplayName("Should thrown an exception when try to delete a product who does not exist")
    public void deleteProduct_Case2() {

        String nonExistingTitle = "nonExistingTitle";

        when(productRepository.findByTitle(nonExistingTitle))
                .thenReturn(null);

        ProductUnexistException exception =
                assertThrows(
                        ProductUnexistException.class,
                        () -> productService.deleteProduct(nonExistingTitle)
                );

        assertEquals("This product does not exist", exception.getMessage() );
        verify(productRepository, times(1)).findByTitle(nonExistingTitle);
        verify(productRepository, times(0)).delete(any());

    }

    @Test
    @DisplayName("Should update a product correctly")
    public void updateProduct_Case1() {

        Long id = 1L;
        Product productEntity = ProductFactory.buildProduct();
        ProductDTO productDTO = ProductFactory.buildProductDto();

        when(productRepository.findById(id))
                .thenReturn(Optional.of(productEntity));

        // Act
        ProductDTO productUpdated = productService.updateProduct(id, productDTO);

        // Assert
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(1)).save(productEntity);

        assertEquals(productDTO.getTitle(), productUpdated.getTitle());
        assertEquals(productDTO.getCategory(), productUpdated.getCategory());
        assertEquals(productDTO.getDescription(), productUpdated.getDescription());
        assertEquals(productDTO.getImage(), productUpdated.getImage());
        assertEquals(productDTO.getPrice(), productUpdated.getPrice());
    }

    @Test
    @DisplayName("Should throw an exception when try to update a product who does not exist")
    public void updateProduct_Case2() {

        Long id = 99L;
        ProductDTO productDTO = ProductFactory.buildProductDto();

        when(productRepository.findById(id))
                .thenReturn(Optional.empty());


        ProductUnexistException exception =
                assertThrows(
                        ProductUnexistException.class,
                        () -> productService.updateProduct(id, productDTO)
                );

        assertEquals("A product with this ID does not exist", exception.getMessage());
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(0)).save(any());
    }


}
