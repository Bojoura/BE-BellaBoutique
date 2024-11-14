package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.DTO.ProductDTO;
import com.bella.BellaBoutique.model.products.Product;
import com.bella.BellaBoutique.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    // Arrange
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = Product.builder()
                .id(1L)
                .title("Product")
                .description("Product Description")
                .price(new BigDecimal("19.99"))
                .build();
    }

    @Test
    public void testConvertToDTO() {
        // Arrange
        // (Al gedaan in de setUp)

        // Act
        ProductDTO productDTO = productService.convertToDTO(product);

        // Assert
        assertNotNull(productDTO);
        assertEquals(product.getTitle(), productDTO.getTitle());
        assertEquals(product.getDescription(), productDTO.getDescription());
        assertEquals(product.getPrice(), productDTO.getPrice());
    }

    @Test
    public void testCreateProduct() {
        // Arrange
        ProductDTO productDTO = ProductDTO.builder()
                .title("New Product")
                .description("New Description")
                .price(new BigDecimal("29.99"))
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product createdProduct = productService.createProduct(productDTO);

        // Assert
        assertNotNull(createdProduct);
        assertEquals("New Product", createdProduct.getTitle());
        assertEquals("New Description", createdProduct.getDescription());
        assertEquals(new BigDecimal("29.99"), createdProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        ProductDTO productDTO = ProductDTO.builder()
                .title("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("39.99"))
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product updatedProduct = productService.updateProduct(1L, productDTO);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getTitle());
        assertEquals("Updated Description", updatedProduct.getDescription());
        assertEquals(new BigDecimal("39.99"), updatedProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testUpdateProductNotFound() {
        // Arrange
        ProductDTO productDTO = ProductDTO.builder()
                .title("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("39.99"))
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Product updatedProduct = productService.updateProduct(1L, productDTO);

        // Assert
        assertNull(updatedProduct);
        verify(productRepository, never()).save(any(Product.class));
    }
}
