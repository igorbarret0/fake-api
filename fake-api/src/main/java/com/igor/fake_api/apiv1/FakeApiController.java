package com.igor.fake_api.apiv1;

import com.igor.fake_api.apiv1.dto.ProductDTO;
import com.igor.fake_api.business.service.FakeApiService;
import com.igor.fake_api.business.service.ProductService;
import com.igor.fake_api.infrastructure.entities.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "fake-api")
public class FakeApiController {

    private FakeApiService fakeApiService;
    private ProductService productService;

    public FakeApiController(FakeApiService fakeApiService, ProductService productService) {
        this.fakeApiService = fakeApiService;
        this.productService = productService;
    }

    @PostMapping("/api")
    @Operation(summary = "Find all products in API and save in database", method = "POST")
    public ResponseEntity<List<ProductDTO>> saveAllProductsApi() {

        var response = fakeApiService.findAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Save a new product in database", method = "POST")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDTO request) {

        productService.saveProduct(request);
        return new ResponseEntity<>("Product saved successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product by ID", method = "PUT")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable(name = "id") Long id,
                                                    @RequestBody ProductDTO request) {

        var response = productService.updateProduct(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all products", method = "GET")
    public ResponseEntity<List<Product>> findAllProducts() {

        var response = productService.findAllProducts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{title}")
    @Operation(summary = "Get a product by title", method = "GET")
    public ResponseEntity<Product> findByTitle(@PathVariable(name = "title") String title) {

        var response = productService.findByTitle(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{title}")
    @Operation(summary = "Delete a product by title", method = "DELETE")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "title") String title) {

        productService.deleteProduct(title);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

}
