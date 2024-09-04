package com.igor.fake_api.business.service;

import com.igor.fake_api.apiv1.dto.ProductDTO;
import com.igor.fake_api.infrastructure.client.FakeApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FakeApiServiceTest {

    @Mock
    FakeApiClient fakeApiClient;

    @Mock
    ProductService productService;

    @InjectMocks
    FakeApiService fakeApiService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should find all products and save them if not already registered")
    void findAllProducts_case1() {

        // Arrange
        ProductDTO product1 = new ProductDTO("Product1", BigDecimal.valueOf(25), "Category1", "Description1", "Image1");
        ProductDTO product2 = new ProductDTO("Product2", BigDecimal.valueOf(20), "Category2", "Description2", "Image2");

        List<ProductDTO> products = List.of(product1, product2);

        when(fakeApiClient.findAllProducts()).thenReturn(products);

        // Act
        List<ProductDTO> result = fakeApiService.findAllProducts();

        // Assert
        assertEquals(2, result.size());
        assertEquals(product1.getTitle(), result.getFirst().getTitle());
        assertEquals(product2.getTitle(), result.getLast().getTitle());
        verify(productService, times(1)).saveProduct(product1);
        verify(productService, times(1)).saveProduct(product2);
    }

    @Test
    @DisplayName("Should handle exception when a product is already registered")
    void findAllProducts_case2() {

        // Arrange
        ProductDTO product1 = new ProductDTO("Product1", BigDecimal.valueOf(25), "Category1", "Description1", "Image1");
        ProductDTO product2 = new ProductDTO("Product2", BigDecimal.valueOf(20), "Category2", "Description2", "Image2");

        List<ProductDTO> products = List.of(product1, product2);

        when(fakeApiClient.findAllProducts()).thenReturn(products);
        doThrow(new RuntimeException("Product already registered")).when(productService).saveProduct(product1);

        // Act
        List<ProductDTO> result = fakeApiService.findAllProducts();

        // Assert
        assertEquals(2, result.size());
        verify(productService, times(1)).saveProduct(product1);  // Exception expected here
        verify(productService, times(1)).saveProduct(product2);  // Should still try to save the second product
    }

}
