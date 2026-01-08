package com.example.arenakart.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.arenakart.DTO.ProductDto;
import com.example.arenakart.Entities.Category;
import com.example.arenakart.Entities.Product;
import com.example.arenakart.Exceptions.ResourceNotFoundException;
import com.example.arenakart.Repositories.CategoryRepository;
import com.example.arenakart.Repositories.ProductRepository;

import java.math.BigDecimal;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        super();
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findByActiveTrue(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Page<Product> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable);
    }

    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchProducts(keyword, pageable);
    }

    public Page<Product> filterProducts(
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String keyword,
            Pageable pageable
    ) {
        if (categoryId != null && minPrice != null && maxPrice != null && keyword != null) {
            return productRepository.findWithFilters(categoryId, minPrice, maxPrice, keyword, pageable);
        } else if (minPrice != null && maxPrice != null) {
            return productRepository.findByPriceRange(minPrice, maxPrice, pageable);
        } else if (keyword != null) {
            return productRepository.searchProducts(keyword, pageable);
        } else if (categoryId != null) {
            return productRepository.findByCategoryIdAndActiveTrue(categoryId, pageable);
        }
        return productRepository.findByActiveTrue(pageable);
    }

    @Transactional
    public Product createProduct(ProductDto dto) {
        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : 0);
        product.setSku(dto.getSku());
        product.setImageUrl(dto.getImageUrl());
        product.setCategory(category);
        product.setActive(true);

        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDto dto) {
        Product product = getProductById(id);

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setSku(dto.getSku());
        product.setImageUrl(dto.getImageUrl());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            product.setCategory(category);
        }

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    @Transactional
    public void updateStock(Long productId, Integer quantity) {
        Product product = getProductById(productId);
        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);
    }

    public boolean checkStock(Long productId, Integer quantity) {
        Product product = getProductById(productId);
        return product.getStockQuantity() >= quantity;
    }
}
