package com.codewithak.dream_shop.controller;

import com.codewithak.dream_shop.dto.ProductDto;
import com.codewithak.dream_shop.exceptions.ProductNotFoundException;
import com.codewithak.dream_shop.exceptions.ResourceNotFoundException;
import com.codewithak.dream_shop.mapper.ProductMapper;
import com.codewithak.dream_shop.model.Product;
import com.codewithak.dream_shop.request.AddProductRequest;
import com.codewithak.dream_shop.request.ProductUpdateRequest;
import com.codewithak.dream_shop.response.ApiResponse;
import com.codewithak.dream_shop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<ProductDto> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("success", ProductMapper.toDto(product)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            ProductDto theProduct = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add product success!", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{prodId}/update")
    public ResponseEntity<ApiResponse> updateResponse(@RequestBody ProductUpdateRequest request, @PathVariable Long prodId) {
        try {
            ProductDto theProduct = productService.updateProduct(request, prodId);
            return ResponseEntity.ok(new ApiResponse("Updated product success!", theProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{prodId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long prodId) {
        try {
            productService.deleteProduct(prodId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<ProductDto> products = productService.getProductsByBrandAndName(brandName, productName);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<ProductDto> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/product/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<ProductDto> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/product/{brand}/products")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand) {
        try {
            List<ProductDto> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<ProductDto> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("No products found", null));
            }
            return ResponseEntity.ok(new ApiResponse("success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error", e.getMessage()));
        }
    }

    @GetMapping("/products/count/by-brand/by-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            Long productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product count: ", productCount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error: ", e.getMessage()));
        }
    }
}
