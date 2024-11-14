package com.bella.BellaBoutique.controller;

import com.bella.BellaBoutique.DTO.ProductDTO;
import com.bella.BellaBoutique.model.products.Product;
import com.bella.BellaBoutique.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;

    @BeforeEach
    public void setUp() {
        // Arrange
        product = Product.builder()
                .id(1L)
                .title("Product")
                .description("Product Description")
                .price(new BigDecimal("19.99"))
                .build();
        productRepository.save(product);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        // Arrange
        // (Zie setUp hierboven)

        // Act
        var resultActions = mockMvc.perform(get("/products"));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is(product.getTitle())))
                .andExpect(jsonPath("$[0].description", is(product.getDescription())))
                .andExpect(jsonPath("$[0].price", is(product.getPrice().doubleValue())));
    }

    @Test
    public void testGetProductById() throws Exception {
        // Arrange
        Long productId = product.getId();

        // Act
        var resultActions = mockMvc.perform(get("/products/{id}", productId));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(product.getTitle())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.price", is(product.getPrice().doubleValue())));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        var resultActions = mockMvc.perform(get("/products/{id}", nonExistentId));

        // Assert
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Arrange
        ProductDTO newProduct = ProductDTO.builder()
                .title("New Product")
                .description("New Description")
                .price(new BigDecimal("29.99"))
                .build();
        String newProductJson = objectMapper.writeValueAsString(newProduct);

        // Act
        var resultActions = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newProductJson));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(newProduct.getTitle())))
                .andExpect(jsonPath("$.description", is(newProduct.getDescription())))
                .andExpect(jsonPath("$.price", is(newProduct.getPrice().doubleValue())));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Arrange
        ProductDTO updatedProduct = ProductDTO.builder()
                .title("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("39.99"))
                .build();
        String updatedProductJson = objectMapper.writeValueAsString(updatedProduct);

        // Act
        var resultActions = mockMvc.perform(put("/products/{id}", product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedProductJson));

        // Assert
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(updatedProduct.getTitle())))
                .andExpect(jsonPath("$.description", is(updatedProduct.getDescription())))
                .andExpect(jsonPath("$.price", is(updatedProduct.getPrice().doubleValue())));
    }

    @Test
    public void testUpdateProduct_NotFound() throws Exception {
        // Arrange
        Long nonExistentId = 999L;
        ProductDTO updatedProduct = ProductDTO.builder()
                .title("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("39.99"))
                .build();
        String updatedProductJson = objectMapper.writeValueAsString(updatedProduct);

        // Act
        var resultActions = mockMvc.perform(put("/products/{id}", nonExistentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedProductJson));

        // Assert
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Arrange
        Long productId = product.getId();

        // Act
        var resultActions = mockMvc.perform(delete("/products/{id}", productId));

        // Assert
        resultActions.andExpect(status().isOk());
        Optional<Product> deletedProduct = productRepository.findById(productId);
        assertTrue(deletedProduct.isEmpty());
    }

    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        // Arrange
        Long nonExistentId = 999L;

        // Act
        var resultActions = mockMvc.perform(delete("/products/{id}", nonExistentId));

        // Assert
        resultActions.andExpect(status().isNotFound());
    }
}
