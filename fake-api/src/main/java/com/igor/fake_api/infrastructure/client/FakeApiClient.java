package com.igor.fake_api.infrastructure.client;

import com.igor.fake_api.apiv1.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "fake-api", url = "${fake-api.url}")
public interface FakeApiClient {

    @GetMapping("/products")
    List<ProductDTO> findAllProducts();

}
