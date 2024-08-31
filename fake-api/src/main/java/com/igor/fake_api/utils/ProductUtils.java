package com.igor.fake_api.utils;

import com.igor.fake_api.apiv1.dto.ProductDTO;
import com.igor.fake_api.infrastructure.entities.Product;
import com.igor.fake_api.infrastructure.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductUtils {

    private static ProductRepository productRepository;

    public static Product updateEntity(Product productEntity, ProductDTO request) {

        if (request.getTitle() != null) {
            productEntity.setTitle(request.getTitle());
        }

        if (request.getCategory() != null) {
            productEntity.setCategory(request.getCategory());
        }

        if (request.getDescription() != null) {
            productEntity.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            productEntity.setPrice(request.getPrice());
        }

        if (request.getImage() != null) {
            productEntity.setImage(request.getImage());
        }

        return productEntity;
    }

    public static ProductDTO entityToDto(Product product) {

        var dto = new ProductDTO();
        dto.setTitle(product.getTitle());
        dto.setCategory(product.getCategory());
        dto.setImage(product.getImage());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());

        return dto;
    }


    public static Product dtoToEntity(ProductDTO dto) {

        var productEntity = new Product();
        productEntity.setTitle(dto.getTitle());
        productEntity.setCategory(dto.getCategory());
        productEntity.setImage(dto.getImage());
        productEntity.setPrice(dto.getPrice());
        productEntity.setDescription(dto.getDescription());

        return productEntity;
    }

}
