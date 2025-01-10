package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.DTO.ProductDTO;
import com.bella.BellaBoutique.DTO.ReviewDTO;
import com.bella.BellaBoutique.model.products.Product;
import com.bella.BellaBoutique.model.reviews.Review;
import com.bella.BellaBoutique.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {
        productDTO = ProductDTO.builder()
                .id(1L)
                .title("Product A")
                .description("Description of Product A")
                .category("beauty")
                .price(BigDecimal.valueOf(100.0))
                .discountPercentage(BigDecimal.valueOf(10.0))
                .rating(BigDecimal.valueOf(4.5))
                .stock(20)
                .brand("Brand A")
                .sku("123ABC")
                .weight(BigDecimal.valueOf(0.5))
                .width(BigDecimal.valueOf(30.0))
                .height(BigDecimal.valueOf(10.0))
                .depth(BigDecimal.valueOf(5.0))
                .warrantyInformation("1 year warranty")
                .shippingInformation("Standard shipping")
                .availabilityStatus("In Stock")
                .returnPolicy("30 days return")
                .minimumOrderQuantity(1)
                .barcode("barcode123")
                .qrCode("qr123")
                .thumbnail("thumb.jpg")
                .images(null)
                .tags(null)
                .reviews(null)
                .build();

        product = new Product();
        ReflectionTestUtils.setField(product, "id", 1L);
        product.setTitle("Product A");
        product.setDescription("Description of Product A");
        product.setCategory("beauty");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setDiscountPercentage(BigDecimal.valueOf(10.0));
        product.setRating(BigDecimal.valueOf(4.5));
        product.setStock(20);
        product.setBrand("Brand A");
        product.setSku("123ABC");
        product.setWeight(BigDecimal.valueOf(0.5));
        product.setWidth(BigDecimal.valueOf(30.0));
        product.setHeight(BigDecimal.valueOf(10.0));
        product.setDepth(BigDecimal.valueOf(5.0));
        product.setWarrantyInformation("1 year warranty");
        product.setShippingInformation("Standard shipping");
        product.setAvailabilityStatus("In Stock");
        product.setReturnPolicy("30 days return");
        product.setMinimumOrderQuantity(1);
        product.setBarcode("barcode123");
        product.setQrCode("qr123");
        product.setThumbnail("thumb.jpg");
        product.setImages(null);
        product.setTags(null);
        product.setReviews(null);
    }

    @Test
    void testConvertToDTO() {
        // Arrange
        Review review = new Review();
        review.setId(1L);
        review.setReviewerName("John Doe");
        review.setComment("Great product!");
        review.setRating(5);
        review.setReviewDate(LocalDateTime.now());
        review.setReviewerEmail("john.doe@example.com");

        product.setReviews(List.of(review));

        // Act
        ProductDTO convertedDTO = productService.convertToDTO(product);

        // Assert
        assertNotNull(convertedDTO);
        assertEquals("Product A", convertedDTO.getTitle());
        assertEquals(BigDecimal.valueOf(100.0), convertedDTO.getPrice());
        assertEquals("beauty", convertedDTO.getCategory());

        assertNotNull(convertedDTO.getReviews());
        assertEquals(1, convertedDTO.getReviews().size());
        ReviewDTO reviewDTO = convertedDTO.getReviews().get(0);
        assertEquals(1L, reviewDTO.getId());
        assertEquals("John Doe", reviewDTO.getReviewerName());
        assertEquals("Great product!", reviewDTO.getComment());
        assertEquals(5, reviewDTO.getRating());
        assertEquals("john.doe@example.com", reviewDTO.getReviewerEmail());
    }

    @Test
    void testConvertToDTONoReviews() {
        // Arrange

        // Act
        ProductDTO convertedDTO = productService.convertToDTO(product);

        // Assert
        assertNotNull(convertedDTO);
        assertTrue(convertedDTO.getReviews().isEmpty(), "Reviews should be an empty list when product has no reviews");
    }

    @Test
    void testConvertToDTOMultipleReviews() {
        // Arrange
        Review review1 = new Review();
        review1.setId(1L);
        review1.setReviewerName("John Doe");
        review1.setComment("Great product!");
        review1.setRating(5);

        Review review2 = new Review();
        review2.setId(2L);
        review2.setReviewerName("Jane Smith");
        review2.setComment("Not bad.");
        review2.setRating(3);

        product.setReviews(List.of(review1, review2));

        // Act
        ProductDTO convertedDTO = productService.convertToDTO(product);

        // Assert
        assertNotNull(convertedDTO);
        assertEquals(2, convertedDTO.getReviews().size());
    }

    @Test
    void testConvertToDTONullFields() {
        // Arrange
        product.setTitle(null);
        product.setCategory(null);

        // Act
        ProductDTO convertedDTO = productService.convertToDTO(product);

        // Assert
        assertNotNull(convertedDTO);
        assertNull(convertedDTO.getTitle(), "Title should be null");
        assertNull(convertedDTO.getCategory(), "Category should be null");

        assertNotNull(convertedDTO.getReviews(), "Reviews should not be null");
        assertTrue(convertedDTO.getReviews().isEmpty(), "Reviews should be an empty list when product has no reviews");
    }

    @Test
    void testCreateProduct() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product createdProduct = productService.createProduct(productDTO);

        // Assert
        assertNotNull(createdProduct);
        assertEquals("Product A", createdProduct.getTitle());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateProductInvalidData() {
        // Arrange
        ProductDTO invalidProductDTO = ProductDTO.builder().title(null).build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(invalidProductDTO));
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testCreateProductWithInvalidCategory() {
        // Arrange
        ProductDTO invalidProductDTO = ProductDTO.builder()
                .title("Product A")
                .category("electronics")
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(invalidProductDTO));
    }

    @Test
    void testCreateProductWithNullTitle() {
        // Arrange
        ProductDTO productDTOWithNullTitle = ProductDTO.builder()
                .title(null)  // Invalid because title cannot be null
                .category("beauty")
                .price(BigDecimal.valueOf(100.0))
                .stock(10)
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(productDTOWithNullTitle));
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testCreateProductDatabaseException() {
        // Arrange
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.createProduct(productDTO));
        verify(productRepository, times(1)).save(any(Product.class));
    }


    @Test
    void testUpdateProduct() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product updatedProduct = productService.updateProduct(1L, productDTO);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals("Product A", updatedProduct.getTitle());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Product updatedProduct = productService.updateProduct(1L, productDTO);

        // Assert
        assertNull(updatedProduct);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProductInvalidData() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        ProductDTO invalidProductDTO = ProductDTO.builder().title(null).build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(1L, invalidProductDTO));
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testUpdateProductWithNullTitle() {
        // Arrange
        ProductDTO productDTOWithNullTitle = ProductDTO.builder()
                .title(null)
                .category("beauty")
                .price(BigDecimal.valueOf(100.0))
                .stock(10)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(1L, productDTOWithNullTitle));
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testUpdateProductDatabaseException() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.updateProduct(1L, productDTO));
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testInvalidCategories() {
        // Arrange
        List<String> invalidCategories = new ArrayList<>();
        invalidCategories.add("electronics");
        invalidCategories.add(null);
        invalidCategories.add("");

        // Act & Assert
        for (String category : invalidCategories) {
            boolean result = productService.isValidCategory(category);
            assertFalse(result, "Expected category '" + category + "' to be invalid");
        }
    }

    @Test
    void testIsValidCategoryNullAndEmpty() {
        // Arrange

        // Act
        boolean nullCategory = productService.isValidCategory(null);
        boolean emptyCategory = productService.isValidCategory("");

        // Assert
        assertFalse(nullCategory, "Null category should be invalid");
        assertFalse(emptyCategory, "Empty category should be invalid");
    }

    @Test
    void testInvalidCategory() {
        // Arrange
        String invalidCategory = "electronics";

        // Act
        boolean result = productService.isValidCategory(invalidCategory);

        // Assert
        assertFalse(result, "Expected 'electronics' to be an invalid category");
    }

    @Test
    void testGetFeaturedProducts() {
        // Arrange
        when(productRepository.findByRatingGreaterThanAndStockGreaterThanOrderByRatingDesc(4.0, 10))
                .thenReturn(List.of(product));

        // Act
        List<Product> featuredProducts = productService.getFeaturedProducts();

        // Assert
        assertNotNull(featuredProducts);
        assertEquals(1, featuredProducts.size());
        assertEquals("Product A", featuredProducts.get(0).getTitle());
    }

    @Test
    void testGetFeaturedProductsNoProducts() {
        // Arrange
        when(productRepository.findByRatingGreaterThanAndStockGreaterThanOrderByRatingDesc(4.0, 10))
                .thenReturn(List.of());

        // Act
        List<Product> featuredProducts = productService.getFeaturedProducts();

        // Assert
        assertNotNull(featuredProducts);
        assertTrue(featuredProducts.isEmpty());
    }

    @Test
    void testGetFeaturedProductsNoFeaturedProducts() {
        // Arrange
        when(productRepository.findByRatingGreaterThanAndStockGreaterThanOrderByRatingDesc(4.0, 10))
                .thenReturn(new ArrayList<>());

        // Act
        List<Product> featuredProducts = productService.getFeaturedProducts();

        // Assert
        assertNotNull(featuredProducts);
        assertTrue(featuredProducts.isEmpty(), "Expected no featured products");
    }

    @Test
    void testGetFeaturedProductsMultipleSameRating() {
        // Arrange
        Product product2 = new Product();
        ReflectionTestUtils.setField(product2, "id", 2L);
        product2.setTitle("Product B");
        product2.setRating(BigDecimal.valueOf(4.5));
        product2.setStock(15);

        when(productRepository.findByRatingGreaterThanAndStockGreaterThanOrderByRatingDesc(4.0, 10))
                .thenReturn(List.of(product, product2));

        // Act
        List<Product> featuredProducts = productService.getFeaturedProducts();

        // Assert
        assertNotNull(featuredProducts);
        assertEquals(2, featuredProducts.size());
    }
}