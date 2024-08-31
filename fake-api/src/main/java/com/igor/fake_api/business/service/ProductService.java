package com.igor.fake_api.business.service;

import com.igor.fake_api.apiv1.dto.ProductDTO;
import com.igor.fake_api.infrastructure.entities.Product;
import com.igor.fake_api.infrastructure.exceptions.ProductAlreadyExistsException;
import com.igor.fake_api.infrastructure.exceptions.ProductUnexistException;
import com.igor.fake_api.infrastructure.repository.ProductRepository;
import com.igor.fake_api.utils.ProductUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
            this.productRepository = productRepository;
    }

    public void saveProduct(ProductDTO product) {

        if (productRepository.existsByTitle(product.getTitle()) ) {
            throw new ProductAlreadyExistsException();
        }

        var productEntity = ProductUtils.dtoToEntity(product);
        productRepository.save(productEntity);
    }

    public List<Product> findAllProducts() {

        return productRepository.findAll();
    }

    public Product findByTitle(String title) {

        var product = productRepository.findByTitle(title);

        if (product == null) {
            throw new ProductUnexistException();
        }

        return product;
    }

    public void deleteProduct(String title) {

        var product = productRepository.findByTitle(title);
        if (product == null) {
            throw new ProductUnexistException();
        }

        productRepository.delete(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO request) {

        var productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ProductUnexistException("A product with this ID does not exist"));

        var productUpdated = ProductUtils.updateEntity(productEntity, request);
        productRepository.save(productUpdated);

        return ProductUtils.entityToDto(productEntity);

    }

}
