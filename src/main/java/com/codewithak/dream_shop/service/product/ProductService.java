package com.codewithak.dream_shop.service.product;

import com.codewithak.dream_shop.dto.ImageDto;
import com.codewithak.dream_shop.dto.ProductDto;
import com.codewithak.dream_shop.exceptions.ResourceNotFoundException;
import com.codewithak.dream_shop.model.Category;
import com.codewithak.dream_shop.model.Image;
import com.codewithak.dream_shop.model.Product;
import com.codewithak.dream_shop.repository.CategoryRepository;
import com.codewithak.dream_shop.repository.ImageRepository;
import com.codewithak.dream_shop.repository.ProductRepository;
import com.codewithak.dream_shop.request.AddProductRequest;
import com.codewithak.dream_shop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductDto addProduct(AddProductRequest request) {

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        request.setCategory(category);
        Product savedProduct = productRepository.save(createProduct(request, category));
        return convertToDTO(savedProduct);
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }


    @Override
    public Product getProductById(Long id)  {
        Product product =  productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        return product;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Product not found!");
                        });
    }

    @Override
    public ProductDto updateProduct(ProductUpdateRequest request, Long productId) {
        Product updatedProduct =  productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return convertToDTO(updatedProduct);
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return productRepository.findByName(name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto convertToDTO(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors.toList());

        productDto.setImages(imageDtos);
        return productDto;
    }
}
