package com.igor.fake_api.apiv1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.igor.fake_api.apiv1.dto.ProductDTO;
import com.igor.fake_api.business.service.FakeApiService;
import com.igor.fake_api.business.service.ProductService;
import com.igor.fake_api.factory.ProductFactory;
import com.igor.fake_api.infrastructure.entities.Product;
import com.igor.fake_api.infrastructure.exceptions.ProductAlreadyExistsException;
import com.igor.fake_api.infrastructure.exceptions.ProductUnexistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FakeApiController.class)
public class FakeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FakeApiService fakeApiService;

    @MockBean
    ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAllProductsApi() throws Exception {

        List<ProductDTO> mockProductList = new ArrayList<>();
        mockProductList.add(new ProductDTO("Product1", BigDecimal.valueOf(50), "Category1", "Description1", "Image1"));
        mockProductList.add(new ProductDTO("Product2", BigDecimal.valueOf(20), "Category2", "Description2", "Image2"));

        when(fakeApiService.findAllProducts()).thenReturn(mockProductList);

        mockMvc.perform(post("/products/api")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Product1"))
                .andExpect(jsonPath("$[1].title").value("Product2"));
    }

    @Test
    public void testSaveNewProductManually() throws Exception {

        ProductDTO productDTO = ProductFactory.buildProductDto();

        doNothing().when(productService).saveProduct(productDTO);

        mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Product saved successfully"));
    }

    @Test
    public void testSaveNewProductManually_WhenProduct_AlreadyExists() throws Exception {

        ProductDTO productDTO = ProductFactory.buildProductDto();

        doThrow(new ProductAlreadyExistsException()).when(productService).saveProduct(any(ProductDTO.class));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))  // Envie o DTO como JSON
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("This product is already registered"))
                .andExpect(jsonPath("$.status").value(409));

    }

    @Test
    public void testUpdatedProduct() throws Exception {

        Long productId = 66L;
        ProductDTO updateProduct = ProductFactory.buildProductDto();

        when(productService.updateProduct(productId, updateProduct)).thenReturn(updateProduct);

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("titleDTO"))
                .andExpect(jsonPath("$.description").value("descriptionDTO"))
                .andExpect(jsonPath("$.image").value("imageDTO"))
                .andExpect(jsonPath("$.category").value("categoryDTO"))
                .andExpect(jsonPath("$.price").value(20.0));
    }

    @Test
    public void testFindAllProducts() throws Exception {

        List<Product> mockProductList = ProductFactory.buildProductsList();

        when(productService.findAllProducts()).thenReturn(mockProductList);

        mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("title1"))
                .andExpect(jsonPath("$[1].title").value("title2"));
    }

    @Test
    public void testFindByTitle() throws Exception {

        String title = "Some Title";
        Product product = ProductFactory.buildProduct();

        when(productService.findByTitle(title)).thenReturn(product);

        mockMvc.perform(get("/products/{title}", title)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.price").value(20.0))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.category").value("category"));

    }

    @Test
    public void testFindByTitle_WhenProduct_DoesNotExist() throws Exception {

        String title = "nonExistingTitle";

        doThrow(new ProductUnexistException())
                .when(productService).findByTitle(title);

        mockMvc.perform(get("/products/{title}", title))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("This product does not exist"))
                .andExpect(jsonPath("$.status").value(404));

    }

    @Test
    public void testDeleteProduct() throws Exception {

        String title = "Some Title";

        doNothing().when(productService).deleteProduct(title);

        mockMvc.perform(delete("/products/{title}", title)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));
    }

    @Test
    public void testDeleteProduct_WhenProduct_DoesNotExist() throws Exception {

        String title = "nonExistingProduct";

        doThrow(new ProductUnexistException())
                .when(productService).deleteProduct(title);

        mockMvc.perform(delete("/products/{title}", title))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("This product does not exist"))
                .andExpect(jsonPath("$.status").value(404));

    }

}
