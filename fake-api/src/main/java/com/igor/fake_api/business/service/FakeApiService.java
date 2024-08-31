package com.igor.fake_api.business.service;

import com.igor.fake_api.apiv1.dto.ProductDTO;
import com.igor.fake_api.infrastructure.client.FakeApiClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakeApiService {

    private FakeApiClient fakeApiClient;
    private ProductService productService;

    public FakeApiService(FakeApiClient fakeApiClient, ProductService productService) {
        this.fakeApiClient = fakeApiClient;
        this.productService = productService;
    }

    public List<ProductDTO> findAllProducts() {

        var response = fakeApiClient.findAllProducts();

        response.forEach(product -> {
            try {
                productService.saveProduct(product);
            } catch (RuntimeException e) {
                System.out.println("Product already registered: " + product.getTitle());
                // Continue with the next product
            }
        });
        return response;
    }

}
