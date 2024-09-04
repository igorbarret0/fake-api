package com.igor.fake_api.factory;

import com.igor.fake_api.apiv1.dto.ProductDTO;
import com.igor.fake_api.infrastructure.entities.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class ProductFactory {

    public static ProductDTO buildProductDto() {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("titleDTO");
        productDTO.setPrice(BigDecimal.valueOf(20.0));
        productDTO.setCategory("categoryDTO");
        productDTO.setDescription("descriptionDTO");
        productDTO.setImage("imageDTO");

        return productDTO;
    }

    public static Product buildProduct() {

        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setTitle("title");
        productDTO1.setPrice(BigDecimal.valueOf(20.0));
        productDTO1.setCategory("category");
        productDTO1.setDescription("description");
        productDTO1.setImage("image");

        Product product = new Product(productDTO1);
        return product;

    }

    public static List<Product> buildProductsList() {

        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setTitle("title1");
        productDTO1.setPrice(BigDecimal.valueOf(20.0));
        productDTO1.setCategory("category1");
        productDTO1.setDescription("description1");
        productDTO1.setImage("image1");

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setTitle("title2");
        productDTO2.setPrice(BigDecimal.valueOf(25.0));
        productDTO2.setCategory("category2");
        productDTO2.setDescription("description2");
        productDTO2.setImage("image2");

        var product1 = new Product(productDTO1);
        var product2 = new Product(productDTO2);


        return Arrays.asList(product1, product2);
    }

}
