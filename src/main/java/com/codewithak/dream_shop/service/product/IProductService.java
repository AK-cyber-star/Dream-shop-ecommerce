package com.codewithak.dream_shop.service.product;

import com.codewithak.dream_shop.dto.ProductDto;
import com.codewithak.dream_shop.model.Product;
import com.codewithak.dream_shop.request.AddProductRequest;
import com.codewithak.dream_shop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    ProductDto addProduct(AddProductRequest product);

    Product getProductById(Long id);

    void deleteProduct(Long id);

    ProductDto updateProduct(ProductUpdateRequest product, Long productId);

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsByCategory(String category);

    List<ProductDto> getProductsByBrand(String brand);

    List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);

    List<ProductDto> getProductsByName(String name);

    List<ProductDto> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

}
